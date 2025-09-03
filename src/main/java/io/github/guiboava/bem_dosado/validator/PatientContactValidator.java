package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.repository.PatientContactRepository;
import io.github.guiboava.bem_dosado.repository.PatientHealthRepository;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientContactValidator {

    private final PatientContactRepository repository;

    public void validate(PatientContact patientContact) {

        //validações aqui.

    }


}
