package io.github.guiboava.bem_dosado.controller.dto;

import java.util.UUID;

public record MedicationResponseDTO(
        UUID id,
        String name,
        Double dosage,
        Integer frequency,
        String observation
        ) {
}
