package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.PatientHealth;

import java.math.BigDecimal;
import java.util.UUID;

public record PatientHealthResponseDTO(UUID id,
                                       Integer bloodPressure,
                                       Integer heartRate,
                                       Integer oximetry,
                                       Integer bloodGlucose,
                                       BigDecimal temperature) {

    public PatientHealth createPatientHealth() {
        PatientHealth patientHealth = new PatientHealth();
        patientHealth.setId(id);
        patientHealth.setBloodPressure(bloodPressure);
        patientHealth.setHeartRate(heartRate);
        patientHealth.setOximetry(oximetry);
        patientHealth.setBloodGlucose(bloodGlucose);
        patientHealth.setTemperature(temperature);
        return patientHealth;
    }

}
