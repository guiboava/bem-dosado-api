package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import io.github.guiboava.bem_dosado.validator.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientValidator validator;
    private final PatientMapper mapper;

    public UUID save(PatientRequestDTO dto) {

        Patient patient = mapper.toEntity(dto);

        validator.validate(patient);
        return repository.save(patient).getId();
    }

    public void update(UUID patientId, PatientRequestDTO dto) {

        Patient patient = getEntityById(patientId);

        mapper.updateEntityFromDto(dto, patient);
        validator.validate(patient);
        repository.save(patient);
    }

    public void delete(UUID patientId) {

        Patient patient = getEntityById(patientId);

        validator.validateNotLinkedToUsers(patient);

        repository.delete(patient);
    }

    public List<PatientResponseDTO> searchByExample(String name,
                                                    String cpf,
                                                    LocalDate birthDate,
                                                    Gender gender,
                                                    String cep,
                                                    Dependency dependency,
                                                    String healthPlan,
                                                    String cardNumber,
                                                    String allergies,
                                                    String medicationsDescription,
                                                    String note) {
        var patient = new Patient();
        patient.setName(name);
        patient.setCpf(cpf);
        patient.setBirthDate(birthDate);
        patient.setGender(gender);
        patient.setCep(cep);
        patient.setDependency(dependency);
        patient.setHealthPlan(healthPlan);
        patient.setCardNumber(cardNumber);
        patient.setAllergies(allergies);
        patient.setMedicationsDescription(medicationsDescription);
        patient.setNote(note);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Patient> patientExample = Example.of(patient, matcher);
        return repository.findAll(patientExample)
                .stream()
                .map(mapper::toDTO)
                .toList();

    }

    public PatientResponseDTO getById(UUID patientId) {
        return repository.findById(patientId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado para o id " + patientId));
    }

    public Patient getEntityById(UUID patientId) {
        return repository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado para o id " + patientId));
    }

}
