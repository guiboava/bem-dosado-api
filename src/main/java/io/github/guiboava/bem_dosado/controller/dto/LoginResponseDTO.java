package io.github.guiboava.bem_dosado.controller.dto;

import java.util.UUID;

public record LoginResponseDTO(
        String token,
        String refreshToken,
        String username,
        UUID userId,
        String email,
        Long expiresIn
) {
}
