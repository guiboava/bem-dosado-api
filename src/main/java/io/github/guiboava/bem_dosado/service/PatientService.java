package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import io.github.guiboava.bem_dosado.validator.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientValidator validator;

    public Patient save(Patient patient) {
        validator.validate(patient);
        return repository.save(patient);
    }

    public List<Patient> searchByExample(String name,
                                         String cpf,
                                         LocalDate birthDate,
                                         Gender gender,
                                         String cep,
                                         Dependency dependency,
                                         String healthPlan,
                                         String cardNumber,
                                         String allergies,
                                         String medications,
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
        patient.setMedications(medications);
        patient.setNote(note);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Patient> patientExample = Example.of(patient, matcher);
        return repository.findAll(patientExample);

    }

    public Optional<Patient> getById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Patient patient) {
        repository.delete(patient);
    }

    public void update(Patient patient) {

        if (patient.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de usuario é nescessário que o usuario esteja salvo na base.");
        }
        validator.validate(patient);
        repository.save(patient);
    }
}
