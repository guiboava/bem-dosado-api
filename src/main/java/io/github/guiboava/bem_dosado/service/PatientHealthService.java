package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientHealthMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.PatientHealthRepository;
import io.github.guiboava.bem_dosado.validator.PatientHealthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientHealthService {

    private final PatientHealthRepository repository;
    private final PatientHealthMapper mapper;
    private final PatientService patientService;
    private final PatientHealthValidator validator;

    public List<PatientHealthResponseDTO> getByPatientId(UUID patientId) {
        Optional<Patient> optionalPatient = patientService.getById(patientId);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            List<PatientHealth> healthRecords = repository.findByPatient(patient);
            return healthRecords.stream()
                    .map(mapper::toDTO)
                    .toList();
        }
        throw new IllegalArgumentException("Para buscar a saúde de um paciente, é necessário que o paciente já esteja salvo na base.");
    }

    public Optional<PatientHealth> getByPatientIdAndHealthId(UUID patientId, UUID healthId) {
        Optional<Patient> optionalPatient = patientService.getById(patientId);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return repository.findByIdAndPatient(healthId, patient);
        }
        throw new IllegalArgumentException("Para buscar a saúde de um paciente, é necessário que o paciente já esteja salvo na base.");
    }

    public PatientHealth save(PatientHealth patientHealth) {
        validator.validate(patientHealth);
        return repository.save(patientHealth);
    }

    public void update(PatientHealth patientHealth) {

        if (patientHealth.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de dado de saúde do Paciente é nescessário que o dado esteja salvo na base.");
        }
        validator.validate(patientHealth);
        repository.save(patientHealth);
    }

    public void delete(PatientHealth patientHealth) {
        repository.delete(patientHealth);
    }
}
