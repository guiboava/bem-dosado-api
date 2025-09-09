package io.github.guiboava.bem_dosado.controller.dto;

import java.util.UUID;

public record PublicEmergencyResponseDTO(
        UUID id,
        String serviceName,
        String phoneNumber,
        String description
) {
}
