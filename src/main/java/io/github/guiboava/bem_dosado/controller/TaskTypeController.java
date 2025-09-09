package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.TaskTypeRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.TaskTypeMapper;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.service.TaskTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-types")
@RequiredArgsConstructor
public class TaskTypeController implements GenericController {


    private final TaskTypeService service;
    private final TaskTypeMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createTaskType(@RequestBody @Valid TaskTypeRequestDTO dto) {

        TaskType taskType = mapper.toEntity(dto);

        service.save(taskType);

        URI uri = generateHeaderLocation(taskType.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{taskTypeId}")
    public ResponseEntity<Void> updateTaskType(
            @PathVariable("taskTypeId") UUID taskTypeId,
            @RequestBody @Valid TaskTypeRequestDTO dto) {

        TaskType taskType = service.getById(taskTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de tipo de tarefa para o id " + taskTypeId));

        mapper.updateEntityFromDto(dto, taskType);

        service.update(taskType);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskTypeId}")
    public ResponseEntity<Void> deleteTaskType(@PathVariable("taskTypeId") UUID taskTypeId) {

        TaskType taskType = service
                .getById(taskTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de tipo de tarefa para o id " + taskTypeId));

        service.delete(taskType);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskTypeResponseDTO>> getAllTaskTypes() {

        List<TaskTypeResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{taskTypeId}")
    public ResponseEntity<TaskTypeResponseDTO> getByTaskTypeId(@PathVariable("taskTypeId") UUID taskTypeId) {

        return service.getById(taskTypeId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de tipo de tarefa para o id " + taskTypeId));
    }
}
