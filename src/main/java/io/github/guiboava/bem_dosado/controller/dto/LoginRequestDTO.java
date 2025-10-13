package io.github.guiboava.bem_dosado.controller.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(

        @NotNull(message = "Username é obrigatório")
        String username,

        @NotNull(message = "Senha é obrigatória")
        String password

) {
}
