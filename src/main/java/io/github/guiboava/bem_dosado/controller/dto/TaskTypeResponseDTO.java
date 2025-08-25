package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.TaskType;

import java.util.UUID;

public record TaskTypeResponseDTO(UUID id, String describe) {

    public TaskType createTaskType() {
        TaskType taskType = new TaskType();
        taskType.setId(id);
        taskType.setDescribe(describe);
        return taskType;
    }

}
