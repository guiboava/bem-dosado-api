package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.TaskMapper;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.TaskRepository;
import io.github.guiboava.bem_dosado.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper mapper;
    private final TaskRepository repository;
    private final TaskValidator validator;
    private final UserService userService;

    public Task save(Task task) {
        validator.validate(task);
        return repository.save(task);
    }

    public List<TaskResponseDTO> getByUserId(UUID userId) {

        Optional<User> optionalUser = userService.getById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Task> taskRecords = repository.findByUser(user);
            return taskRecords.stream()
                    .map(mapper::toDTO)
                    .toList();
        }
        throw new IllegalArgumentException("Para buscar as tarefas de um usuario, é necessário que o usuario já esteja salvo na base.");
    }

    public Optional<Task> getByIdAndUserId( UUID taskId, UUID userId) {
        Optional<User> optionalUser = userService.getById(userId);

        if (optionalUser.isPresent()) {
            User User = optionalUser.get();
            return repository.findByIdAndUser(taskId, User);
        }
        throw new IllegalArgumentException("Para buscar as tarefas de um usuario, é necessário que o usuario já esteja salvo na base.");
    }


    public void update(Task task, User user) {

        if (task.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de dado de task é nescessário que o dado esteja salvo na base.");
        }

        validator.validateConsistency(task, user);
        repository.save(task);

    }

    public void delete(Task task, User user) {
        validator.validateConsistency(task,user);
        repository.delete(task);
    }
}
