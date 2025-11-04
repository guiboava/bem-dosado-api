package io.github.guiboava.bem_dosado.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PatientHealthResponseDTO(UUID id,
                                       Integer bloodPressure,
                                       Integer heartRate,
                                       Integer oximetry,
                                       Integer bloodGlucose,
                                       BigDecimal temperature,
                                       LocalDateTime createdDate) {
}

