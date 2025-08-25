package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;

import java.time.LocalDate;

public record PatientRequestDTO(String name,
                                LocalDate birthDate,
                                Gender gender,
                                String cep,
                                Dependency dependency) {

    public Patient createPatient() {
        Patient patient = new Patient();
        patient.setName(name);
        patient.setBirthDate(birthDate);
        patient.setGender(gender);
        patient.setCep(cep);
        patient.setDependency(dependency);
        return patient;
    }
}
