package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.TaskTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskTypeValidator {

    final private TaskTypeRepository repository;

    public void validate(TaskType taskType) {

        duplicateRegister(taskType);
        notEmptyDescribe(taskType);
    }

    private void notEmptyDescribe(TaskType taskType) {
        if (taskType.getDescribe() == null || taskType.getDescribe().isBlank()) {
            throw new OperationNotPermittedException("A descrição do tipo de tarefa não pode estar vazia.");
        }
    }

    private void duplicateRegister(TaskType taskType) {
        boolean exists;
        if (taskType.getId() == null) {
            exists = repository.existsByDescribeContainingIgnoreCase(taskType.getDescribe());
        } else {
            exists = repository.existsByDescribeContainingIgnoreCaseAndIdNot(taskType.getDescribe(), taskType.getId());
        }

        if (exists) {
            throw new DuplicateRegisterException("Já existe um tipo de tarefa com esse nome.");
        }
    }





}
