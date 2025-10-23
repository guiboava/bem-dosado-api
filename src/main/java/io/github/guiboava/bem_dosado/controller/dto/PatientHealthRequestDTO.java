package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(name = "Saúde Paciente")
public record PatientHealthRequestDTO(

        @NotNull(message = "A pressão arterial não pode ser nula")
        @Min(value = 40, message = "A pressão arterial mínima deve ser no mínimo 40 mmHg")
        @Max(value = 250, message = "A pressão arterial máxima deve ser no máximo 250 mmHg")
        @Schema(name = "pressaoSangue")
        Integer bloodPressure,

        @NotNull(message = "A frequência cardíaca não pode ser nula")
        @Min(value = 30, message = "A frequência cardíaca mínima deve ser no mínimo 30 bpm")
        @Max(value = 220, message = "A frequência cardíaca máxima deve ser no máximo 220 bpm")
        @Schema(name = "batimentoCardiaco")
        Integer heartRate,

        @NotNull(message = "A oximetria não pode ser nula")
        @Min(value = 50, message = "A oximetria mínima deve ser no mínimo 50%")
        @Max(value = 100, message = "A oximetria máxima deve ser no máximo 100%")
        @Schema(name = "oximetria")
        Integer oximetry,

        @NotNull(message = "A glicemia não pode ser nula")
        @Min(value = 40, message = "A glicemia mínima deve ser no mínimo 40 mg/dL")
        @Max(value = 600, message = "A glicemia máxima deve ser no máximo 600 mg/dL")
        @Schema(name = "glucoseSangue")
        Integer bloodGlucose,

        @NotNull(message = "A temperatura não pode ser nula")
        @DecimalMin(value = "30.0", message = "A temperatura mínima deve ser no mínimo 30°C")
        @DecimalMax(value = "45.0", message = "A temperatura máxima deve ser no máximo 45°C")
        @Schema(name = "temperatura")
        BigDecimal temperature
) {
}
