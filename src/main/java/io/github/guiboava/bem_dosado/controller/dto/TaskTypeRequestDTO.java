package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.TaskType;

public record TaskTypeRequestDTO(String describe) {

    public TaskType createTaskType() {
        TaskType taskType = new TaskType();
        taskType.setDescribe(describe);
        return taskType;
    }

}
