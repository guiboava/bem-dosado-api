package io.github.guiboava.bem_dosado.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequestDTO(

        @NotBlank(message = "A descrição não pode estar vazia")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String describe,

        @NotNull(message = "A data agendada não pode ser nula")
        @FutureOrPresent(message = "A data agendada deve ser no presente ou no futuro")
        LocalDateTime scheduledDate,

        @NotNull(message = "O paciente é obrigatório")
        UUID patientId,

        @NotNull(message = "O paciente é obrigatório")
        UUID taskTypeId
) {
}
