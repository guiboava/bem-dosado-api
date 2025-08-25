package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.PatientHealth;

import java.math.BigDecimal;

public record PatientHealthRequestDTO(Integer bloodPressure,
                                      Integer heartRate,
                                      Integer oximetry,
                                      Integer bloodGlucose,
                                      BigDecimal temperature) {

    public PatientHealth createPatientHealth() {
        PatientHealth patientHealth = new PatientHealth();
        patientHealth.setBloodPressure(bloodPressure);
        patientHealth.setHeartRate(heartRate);
        patientHealth.setOximetry(oximetry);
        patientHealth.setBloodGlucose(bloodGlucose);
        patientHealth.setTemperature(temperature);
        return patientHealth;
    }

}
