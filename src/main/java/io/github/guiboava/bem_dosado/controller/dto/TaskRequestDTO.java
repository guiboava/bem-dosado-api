package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Schema(name = "Tarefa")
public record TaskRequestDTO(

        @NotBlank(message = "A descrição não pode estar vazia")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @Schema(name = "descricao")
        String describe,

        @NotNull(message = "A data agendada não pode ser nula")
        @FutureOrPresent(message = "A data agendada deve ser no presente ou no futuro")
        @Schema(name = "dataDeAgendamento")
        LocalDateTime scheduledDate,

        @NotNull(message = "O nível de prioridade deve ser informado")
        @Min(value = 1, message = "O nível deve ser no mínimo 1")
        @Max(value = 5, message = "O nível deve ser no máximo 5")
        @Schema(name = "prioridade")
        Integer priority,

        @NotNull(message = "O paciente é obrigatório")
        @Schema(name = "idPaciente")
        UUID patientId,

        @NotNull(message = "O paciente é obrigatório")
        @Schema(name = "idTipoDeTarefa")
        UUID taskTypeId,

        @NotNull(message = "A lista de medicamentos é obrigatório")
        @Schema(name = "idMedicacao")
        Set<UUID> medicationsIds

) {
}
