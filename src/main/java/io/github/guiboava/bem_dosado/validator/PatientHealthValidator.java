package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.repository.PatientHealthRepository;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientHealthValidator {

    private final PatientHealthRepository repository;

    public void validate(PatientHealth patientHealth) {

        //validações aqui.

    }


}
