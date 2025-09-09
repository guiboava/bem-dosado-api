package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskValidator {

    private final TaskRepository repository;

    public void validateConsistency(Task task, User user) {
        if (!Objects.equals(task.getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("A tarefa não pertence ao usuário informado.");
        }
    }

    public void validate(Task task) {


    }


}
