package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.PatientHealthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PatientHealthValidator {

    private final PatientHealthRepository repository;

    public void validate(PatientHealth patientHealth) {

        validateRanges(patientHealth);

    }

    public void validateRanges(PatientHealth patientHealth) {
        if (patientHealth.getBloodPressure() < 40 || patientHealth.getBloodPressure() > 250) {
            throw new OperationNotPermittedException("Pressão arterial fora do intervalo permitido");
        }
        if (patientHealth.getTemperature().compareTo(BigDecimal.valueOf(30.0)) < 0 ||
                patientHealth.getTemperature().compareTo(BigDecimal.valueOf(45.0)) > 0) {
            throw new OperationNotPermittedException("Temperatura fora do intervalo permitido");
        }
    }


}
