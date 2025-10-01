package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientHealthMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.PatientHealthRepository;
import io.github.guiboava.bem_dosado.validator.PatientHealthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientHealthService {

    private final PatientHealthRepository repository;
    private final PatientHealthMapper mapper;
    private final PatientService patientService;
    private final PatientHealthValidator validator;

    public UUID save(PatientHealthRequestDTO dto, UUID patientId) {

        Patient patient = getPatientOrThrow(patientId);
        PatientHealth patientHealth = mapper.toEntity(dto);

        patientHealth.setPatient(patient);

        validator.validate(patientHealth);
        return repository.save(patientHealth).getId();
    }

    public void update(PatientHealthRequestDTO dto, UUID patientId, UUID healthId) {

        Patient patient = getPatientOrThrow(patientId);

        PatientHealth patientHealth = repository.findByIdAndPatient(healthId, patient)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + healthId));

        mapper.updateEntityFromDto(dto, patientHealth);

        validator.validate(patientHealth);

        repository.save(patientHealth);
    }

    public void delete(UUID patientId, UUID healthId) {

        Patient patient = getPatientOrThrow(patientId);

        PatientHealth patientHealth = repository.findByIdAndPatient(healthId, patient)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + healthId));

        repository.delete(patientHealth);
    }

    public List<PatientHealthResponseDTO> getByPatientId(UUID patientId) {

        Patient patient = getPatientOrThrow(patientId);

        return repository.findByPatient(patient).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public PatientHealthResponseDTO getByPatientIdAndHealthId(UUID patientId, UUID healthId) {

        Patient patient = getPatientOrThrow(patientId);

        return repository.findByIdAndPatient(healthId, patient)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + healthId));
    }

    private Patient getPatientOrThrow(UUID patientId) {
        return patientService.getEntityById(patientId);
    }

}
