package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.Medication;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record TaskRequestDTO(

        @NotBlank(message = "A descrição não pode estar vazia")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String describe,

        @NotNull(message = "A data agendada não pode ser nula")
        @FutureOrPresent(message = "A data agendada deve ser no presente ou no futuro")
        LocalDateTime scheduledDate,

        @NotNull(message = "O nível de prioridade deve ser informado")
        @Min(value = 1, message = "O nível deve ser no mínimo 1")
        @Max(value = 5, message = "O nível deve ser no máximo 5")
        Integer priority,

        @NotNull(message = "O paciente é obrigatório")
        UUID patientId,

        @NotNull(message = "O paciente é obrigatório")
        UUID taskTypeId,

        Set<UUID> medicationsIds

) {
}
