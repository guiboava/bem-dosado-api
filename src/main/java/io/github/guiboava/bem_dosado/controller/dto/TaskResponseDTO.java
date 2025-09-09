package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.TaskType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(UUID id,
                              String describe,
                              LocalDateTime scheduledDate,
                              UUID patientId,
                              UUID taskTypeId) {
}
