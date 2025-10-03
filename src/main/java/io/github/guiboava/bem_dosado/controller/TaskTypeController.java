package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.TaskTypeRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeResponseDTO;
import io.github.guiboava.bem_dosado.service.TaskTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-types")
@RequiredArgsConstructor
public class TaskTypeController implements GenericController {


    private final TaskTypeService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> createTaskType(@RequestBody @Valid TaskTypeRequestDTO dto) {

        URI uri = generateHeaderLocation(service.save(dto));

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{taskTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> updateTaskType(
            @PathVariable("taskTypeId") UUID taskTypeId,
            @RequestBody @Valid TaskTypeRequestDTO dto) {

        service.update(taskTypeId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> deleteTaskType(@PathVariable("taskTypeId") UUID taskTypeId) {

        service.delete(taskTypeId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<List<TaskTypeResponseDTO>> getAllTaskTypes() {

        return ResponseEntity.ok(service.getAll());

    }

    @GetMapping("/{taskTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<TaskTypeResponseDTO> getByTaskTypeId(@PathVariable("taskTypeId") UUID taskTypeId) {

        return ResponseEntity.ok(service.getById(taskTypeId));
    }
}
