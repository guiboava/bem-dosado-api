package io.github.guiboava.bem_dosado.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskTypeRequestDTO(
        @NotBlank(message = "A descrição do tipo de tarefa é obrigatória")
        @Size(max = 50, message = "A descrição não pode ter mais de 50 caracteres")
        String describe) {
}
