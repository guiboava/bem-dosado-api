package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Medicação")
public record MedicationRequestDTO(
        @NotBlank(message = "O nome do medicamento é obrigatório")
        @Schema(name = "nome")
        String name,

        @NotNull(message = "A dosagem é obrigatória")
        @Schema(name = "dosagem")
        Double dosage,

        @NotNull(message = "A frequência é obrigatória")
        @Schema(name = "frequencia")
        Integer frequency,

        @NotBlank(message = "A observação é obrigatória")
        @Schema(name = "observacao")
        String observation
) {}
