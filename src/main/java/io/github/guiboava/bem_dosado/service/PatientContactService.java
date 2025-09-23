package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientContactResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientContactMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.PatientContactRepository;
import io.github.guiboava.bem_dosado.validator.PatientContactValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientContactService {

    private final PatientContactRepository repository;
    private final PatientContactMapper mapper;
    private final PatientContactValidator validator;
    private final PatientService patientService;


    public PatientContact save(PatientContact patientContact) {
        validator.validate(patientContact);
        return repository.save(patientContact);
    }
//
    public void update(PatientContact patientContact) {

        if (patientContact.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de dado de contato do Paciente é nescessário que o dado esteja salvo na base.");
        }
        validator.validate(patientContact);
        repository.save(patientContact);

    }

    public void delete(PatientContact patientContact) {
        repository.delete(patientContact);
    }

    public List<PatientContactResponseDTO> getByPatientId(UUID patientId) {
        Optional<Patient> optionalPatient = patientService.getById(patientId);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            List<PatientContact> contactRecords = repository.findByPatient(patient);
            return contactRecords.stream()
                    .map(mapper::toDTO)
                    .toList();
        }
        throw new OperationNotPermittedException("Para buscar os contatos de um paciente, é necessário que o paciente já esteja salvo na base.");
    }

    public Optional<PatientContact> getByPatientIdAndContactId(UUID patientId, UUID contactId) {
        Optional<Patient> optionalPatient = patientService.getById(patientId);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return repository.findByIdAndPatient(contactId, patient);
        }
        throw new OperationNotPermittedException("Para buscar os contatos de um paciente, é necessário que o paciente já esteja salvo na base.");
    }

}
