package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PageDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tarefa")
public class TaskController implements GenericController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Salvar", description = "Criar uma nova tarefa dentro do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Inautorizado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    public ResponseEntity<Void> createUserTask(@AuthenticationPrincipal User user, @RequestBody @Valid TaskRequestDTO dto) {

        URI uri = generateHeaderLocation(taskService.save(user.getId(), dto));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar.", description = "Atualizar uma tarefa dentro do sistema.")
    public ResponseEntity<Void> updateTask(@AuthenticationPrincipal User user, @PathVariable("taskId") UUID taskId, @RequestBody @Valid TaskRequestDTO dto) {

        taskService.update(user.getId(), taskId, dto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Deletar.", description = "Deletar uma tarefa dentro do sistema.")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user, @PathVariable("taskId") UUID taskId) {

        taskService.delete(user.getId(), taskId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Listar tarefas por usuários dentro do sistema.")
    public ResponseEntity<PageDTO<TaskResponseDTO>> getAllByUserId(@AuthenticationPrincipal User user, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(taskService.getByUserId(user.getId(), page, pageSize));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Encontrar.", description = "Pesquisar por uma tarefa por usuário dentro do sistema.")
    public ResponseEntity<TaskResponseDTO> getByTaskId(@AuthenticationPrincipal User user, @PathVariable("taskId") UUID taskId) {

        return taskService.getByIdAndUserId(user.getId(), taskId);

    }

}
