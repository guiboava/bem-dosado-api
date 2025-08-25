package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.Task;

import java.time.LocalDateTime;

public record TaskRequestDTO(String describe,
                             LocalDateTime scheduledDate) {

    public Task createTask() {
        Task task = new Task();
        task.setDescribe(describe);
        task.setScheduledDate(scheduledDate);
        return task;
    }

}
