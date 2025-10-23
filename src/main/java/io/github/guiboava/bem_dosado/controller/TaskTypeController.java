package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.TaskTypeRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeResponseDTO;
import io.github.guiboava.bem_dosado.service.TaskTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tipos de tarefa")
public class TaskTypeController implements GenericController {


    private final TaskTypeService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Salvar", description = "Criar um novo tipo de tarefa dentro do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Inautorizado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    public ResponseEntity<Void> createTaskType(@RequestBody @Valid TaskTypeRequestDTO dto) {

        URI uri = generateHeaderLocation(service.save(dto));

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{taskTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar.", description = "Atualizar um tipo de tarefa dentro do sistema.")
    public ResponseEntity<Void> updateTaskType(
            @PathVariable("taskTypeId") UUID taskTypeId,
            @RequestBody @Valid TaskTypeRequestDTO dto) {

        service.update(taskTypeId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Deletar.", description = "Deletar um tipo de tarefa dentro do sistema.")
    public ResponseEntity<Void> deleteTaskType(@PathVariable("taskTypeId") UUID taskTypeId) {

        service.delete(taskTypeId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Listar os tipo de tarefa dentro do sistema.")
    public ResponseEntity<List<TaskTypeResponseDTO>> getAllTaskTypes() {

        return ResponseEntity.ok(service.getAll());

    }

    @GetMapping("/{taskTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Encontrar.", description = "Pesquisar por um tipo de tarefa dentro do sistema.")
    public ResponseEntity<TaskTypeResponseDTO> getByTaskTypeId(@PathVariable("taskTypeId") UUID taskTypeId) {

        return ResponseEntity.ok(service.getById(taskTypeId));
    }
}
