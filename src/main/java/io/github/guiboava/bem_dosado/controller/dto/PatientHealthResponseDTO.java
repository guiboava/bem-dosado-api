package io.github.guiboava.bem_dosado.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

// DTO de health vinculado ao paciente
public record PatientHealthResponseDTO(UUID id,
                                       Integer bloodPressure,
                                       Integer heartRate,
                                       Integer oximetry,
                                       Integer bloodGlucose,
                                       BigDecimal temperature) {
}

