package io.github.guiboava.bem_dosado.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicationRequestDTO(
        @NotBlank(message = "O nome do medicamento é obrigatório")
        String name,

        @NotNull(message = "A dosagem é obrigatória")
        Double dosage,

        @NotNull(message = "A frequência é obrigatória")
        Integer frequency,

        @NotBlank(message = "A observação é obrigatória")
        String observation
) {}
