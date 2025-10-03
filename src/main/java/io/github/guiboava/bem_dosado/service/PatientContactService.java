package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientContactRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientContactResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientContactMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.PatientContactRepository;
import io.github.guiboava.bem_dosado.security.SecurityService;
import io.github.guiboava.bem_dosado.validator.PatientContactValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientContactService {

    private final PatientContactRepository repository;
    private final PatientContactMapper mapper;
    private final PatientContactValidator validator;
    private final PatientService patientService;

    public UUID save(PatientContactRequestDTO dto, UUID patientId) {

        Patient patient = getPatientOrThrow(patientId);
        PatientContact patientContact = mapper.toEntity(dto);
        patientContact.setPatient(patient);
        validator.validate(patientContact);


        return repository.save(patientContact).getId();
    }

    public void update(PatientContactRequestDTO dto, UUID patientId, UUID patientContactId) {

        Patient patient = getPatientOrThrow(patientId);

        PatientContact patientContact = repository.findByIdAndPatient(patientContactId, patient)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de contato para o id " + patientContactId));

        mapper.updateEntityFromDto(dto, patientContact);
        validator.validate(patientContact);

        repository.save(patientContact);
    }

    public void delete(UUID patientId, UUID patientContactId) {

        Patient patient = getPatientOrThrow(patientId);

        PatientContact patientContact = repository.findByIdAndPatient(patientContactId, patient)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de contato para o id " + patientContactId));

        repository.delete(patientContact);
    }


    public List<PatientContactResponseDTO> getByPatientId(UUID patientId) {

        Patient patient = getPatientOrThrow(patientId);

        List<PatientContact> contactRecords = repository.findByPatient(patient);
        return contactRecords.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public PatientContactResponseDTO getByPatientIdAndContactId(UUID patientId, UUID patientContactId) {
        Patient patient = getPatientOrThrow(patientId);

        return repository.findByIdAndPatient(patientContactId, patient)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de contato para o id " + patientContactId));
    }


    private Patient getPatientOrThrow(UUID patientId) {
        return patientService.getEntityById(patientId);
    }

}
