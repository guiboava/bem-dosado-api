package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.Task;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(UUID id,
                              String describe,
                              LocalDateTime scheduledDate) {

    public Task createTask() {
        Task task = new Task();
        task.setId(id);
        task.setDescribe(describe);
        task.setScheduledDate(scheduledDate);
        return task;
    }

}
