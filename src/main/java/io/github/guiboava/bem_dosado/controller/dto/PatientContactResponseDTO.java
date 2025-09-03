package io.github.guiboava.bem_dosado.controller.dto;

import java.util.UUID;

public record PatientContactResponseDTO(UUID id,
                                        String name,
                                        String phoneNumber,
                                        String affiliation) {
}
