package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.TaskTypeMapper;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.TaskTypeRepository;
import io.github.guiboava.bem_dosado.validator.TaskTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskTypeService {

    private final TaskTypeRepository repository;
    private final TaskTypeValidator validator;
    private final TaskTypeMapper mapper;

    public Optional<TaskType> getById(UUID taskTypeId) {
        return repository.findById(taskTypeId);
    }

    public TaskType save(TaskType taskType) {

        validator.validate(taskType);
        return repository.save(taskType);

    }

    public void update(TaskType taskType) {

        if(taskType.getId() == null){
            throw new OperationNotPermittedException("Para atualizar o cadastro de um tipo de tarefa é nescessário que o dado esteja salvo na base.");
        }

        validator.validate(taskType);
        repository.save(taskType);

    }

    public void delete(TaskType taskType) {
        repository.delete(taskType);
    }

    public List<TaskTypeResponseDTO> getAll() {

        List<TaskType> taskTypesList = repository.findAll();

        return taskTypesList.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
