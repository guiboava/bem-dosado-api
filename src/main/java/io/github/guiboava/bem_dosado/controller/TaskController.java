package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PageDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users/tasks")
@RequiredArgsConstructor
public class TaskController implements GenericController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> createUserTask(@AuthenticationPrincipal User user, @RequestBody @Valid TaskRequestDTO dto) {

        URI uri = generateHeaderLocation(taskService.save(user.getId(), dto));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> updateTask(@AuthenticationPrincipal User user, @PathVariable("taskId") UUID taskId, @RequestBody @Valid TaskRequestDTO dto) {

        taskService.update(user.getId(), taskId, dto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user, @PathVariable("taskId") UUID taskId) {

        taskService.delete(user.getId(), taskId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<PageDTO<TaskResponseDTO>> getAllByUserId(@AuthenticationPrincipal User user, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(taskService.getByUserId(user.getId(), page, pageSize));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<TaskResponseDTO> getByTaskId(@AuthenticationPrincipal User user, @PathVariable("taskId") UUID taskId) {

        return taskService.getByIdAndUserId(user.getId(), taskId);

    }

}
