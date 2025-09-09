package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.TaskMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.service.PatientService;
import io.github.guiboava.bem_dosado.service.TaskService;
import io.github.guiboava.bem_dosado.service.TaskTypeService;
import io.github.guiboava.bem_dosado.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/tasks")
@RequiredArgsConstructor
public class TaskController implements GenericController {

    private final TaskService taskService;
    private final TaskMapper mapper;
    private final UserService userService;
    private final PatientService patientService;
    private final TaskTypeService taskTypeService;

    @PostMapping
    public ResponseEntity<Void> createUserTask(@PathVariable("userId") UUID userId, @RequestBody @Valid TaskRequestDTO dto) {

        User user = getUserOrThrow(userId);
        Patient patient = getPatientOrThrow(dto.patientId());
        TaskType taskType = getTaskTypeOrThrow(dto.taskTypeId());

        Task task = mapper.toEntity(dto);

        task.setUser(user);
        task.setPatient(patient);
        task.setTaskType(taskType);
        taskService.save(task);

        URI uri = generateHeaderLocation(task.getId());

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable("userId") UUID userId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody @Valid TaskRequestDTO dto) {

        User user = getUserOrThrow(userId);
        Patient patient = getPatientOrThrow(dto.patientId());
        TaskType taskType = getTaskTypeOrThrow(dto.taskTypeId());

        Task task = taskService.getByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado a tarefa para o id " + taskId));

        mapper.updateEntityFromDto(dto, patient, taskType, task);
        taskService.update(task, user);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("userId") UUID userId,
            @PathVariable("taskId") UUID taskId) {

        User user = getUserOrThrow(userId);

        Task task = taskService
                .getByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de tarefa para o id " + taskId));

        taskService.delete(task, user);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllByUserId(@PathVariable("userId") UUID userId) {

        getUserOrThrow(userId);

        List<TaskResponseDTO> list = taskService.getByUserId(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getByHealthId(@PathVariable("userId") UUID userId, @PathVariable("taskId") UUID taskId) {

        return taskService.getByIdAndUserId(taskId, userId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + taskId));
    }

    private User getUserOrThrow(UUID userId) {
        return userService.getById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Erro ao encontrar usuário para o id: " + userId));
    }

    private Patient getPatientOrThrow(UUID patientId) {
        return patientService.getById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado para o id:  " + patientId));
    }

    private TaskType getTaskTypeOrThrow(UUID taskTypeId) {
        return taskTypeService.getById(taskTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de tarefa não encontrado para o id: " + taskTypeId));
    }

}
