package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.TaskTypeRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.TaskTypeMapper;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.TaskTypeRepository;
import io.github.guiboava.bem_dosado.validator.TaskTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskTypeService {

    private final TaskTypeRepository repository;
    private final TaskTypeValidator validator;
    private final TaskTypeMapper mapper;

    public UUID save(TaskTypeRequestDTO dto) {

        TaskType taskType = mapper.toEntity(dto);

        validator.validate(taskType);
        return repository.save(taskType).getId();

    }

    public void update(UUID taskTypeId, TaskTypeRequestDTO dto) {

        TaskType taskType = getEntityById(taskTypeId);

        mapper.updateEntityFromDto(dto, taskType);

        validator.validate(taskType);
        repository.save(taskType);

    }

    public void delete(UUID taskTypeId) {

        TaskType taskType = getEntityById(taskTypeId);

        repository.delete(taskType);
    }

    public List<TaskTypeResponseDTO> getAll() {

        List<TaskType> taskTypesList = repository.findAll();

        return taskTypesList.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TaskType getEntityById(UUID taskTypeId) {
        return repository.findById(taskTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de tipo de tarefa para o id " + taskTypeId));
    }

    public TaskTypeResponseDTO getById(UUID taskTypeId) {
        return repository.findById(taskTypeId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de tipo de tarefa para o id " + taskTypeId));
    }

}
