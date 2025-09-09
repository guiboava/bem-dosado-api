package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record SimplePatientResponseDTO(UUID id,
                                       String name) {
}


