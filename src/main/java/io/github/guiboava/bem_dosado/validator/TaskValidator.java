package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskValidator {

    private final TaskRepository repository;

    public void validate(Task task, User user, Patient patient, TaskType taskType) {

        validateConsistency(task, user, patient, taskType);
        duplicateRegister(task, user, patient, taskType);

    }

    private void duplicateRegister(Task task, User user, Patient patient, TaskType taskType) {

        boolean exists;
        if (task.getId() == null) {
            exists = repository.existsByPatientAndTaskTypeAndScheduledDate(
                    patient,
                    taskType,
                    task.getScheduledDate()
            );
        } else {
            exists = repository.existsByPatientAndTaskTypeAndScheduledDateAndIdNot(
                    patient,
                    taskType,
                    task.getScheduledDate(),
                    task.getId()
            );
        }

        if (exists) {
            throw new DuplicateRegisterException(
                    "Já existe uma tarefa para este paciente com este tipo e data."
            );
        }

    }


    public void validateConsistency(Task task, User user, Patient patient, TaskType taskType) {
        if (!Objects.equals(task.getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("A tarefa não pertence ao usuário informado.");
        }
        if (!Objects.equals(task.getPatient().getId(), patient.getId())) {
            throw new OperationNotPermittedException("A tarefa não pertence ao paciente informado.");
        }
        if (!Objects.equals(task.getTaskType().getId(), taskType.getId())) {
            throw new OperationNotPermittedException("O tipo da tarefa informado não corresponde ao cadastro da tarefa.");
        }
    }

}
