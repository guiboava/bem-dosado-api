package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PageDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/tasks")
@RequiredArgsConstructor
public class TaskController implements GenericController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> createUserTask(@PathVariable("userId") UUID userId, @RequestBody @Valid TaskRequestDTO dto) {

        URI uri = generateHeaderLocation(taskService.save(userId, dto));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> updateTask(@PathVariable("userId") UUID userId, @PathVariable("taskId") UUID taskId, @RequestBody @Valid TaskRequestDTO dto) {

        taskService.update(userId, taskId, dto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("userId") UUID userId, @PathVariable("taskId") UUID taskId) {

        taskService.delete(userId, taskId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<PageDTO<TaskResponseDTO>> getAllByUserId(@PathVariable("userId") UUID userId, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(taskService.getByUserId(userId, page, pageSize));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<TaskResponseDTO> getByTaskId(@PathVariable("userId") UUID userId, @PathVariable("taskId") UUID taskId) {

        return taskService.getByIdAndUserId(userId, taskId);

    }

}
