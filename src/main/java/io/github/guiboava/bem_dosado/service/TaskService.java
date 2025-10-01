package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PageDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.TaskMapper;
import io.github.guiboava.bem_dosado.entity.model.*;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.MedicationRepository;
import io.github.guiboava.bem_dosado.repository.TaskRepository;
import io.github.guiboava.bem_dosado.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper mapper;
    private final TaskRepository taskRepository;
    private final MedicationRepository medicationRepository;
    private final TaskValidator validator;
    private final UserService userService;
    private final PatientService patientService;
    private final TaskTypeService taskTypeService;

    public UUID save(UUID userId, TaskRequestDTO dto) {

        User user = getUserOrThrow(userId);
        Patient patient = getPatientOrThrow(dto.patientId());
        TaskType taskType = getTaskTypeOrThrow(dto.taskTypeId());

        Task task = mapper.toEntity(dto);

        Set<Medication> medications = dto.medicationsIds().stream()
                .map(medicationId -> medicationRepository.findById(medicationId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Não foi encontrado o medicamento: " + medicationId)))
                .collect(Collectors.toSet());

        task.setMedications(medications);
        task.setUser(user);
        task.setPatient(patient);
        task.setTaskType(taskType);

        validator.validate(task, user, patient, taskType);
        return taskRepository.save(task).getId();
    }

    public void update(UUID userId, UUID taskId, TaskRequestDTO dto) {

        User user = getUserOrThrow(userId);
        Patient patient = getPatientOrThrow(dto.patientId());
        TaskType taskType = getTaskTypeOrThrow(dto.taskTypeId());

        Task task = getEntityByIdAndUserId(taskId, userId);

        mapper.updateEntityFromDto(dto, patient, taskType, task);

        Set<Medication> medications = dto.medicationsIds().stream()
                .map(medicationId -> medicationRepository.findById(medicationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o medicamento: " + medicationId)))
                .collect(Collectors.toSet());

        task.setMedications(medications);
        validator.validate(task, user, patient, taskType);
        taskRepository.save(task);

    }

    public void delete(UUID userId, UUID taskId) {

        getUserOrThrow(userId);

        Task task = getEntityByIdAndUserId(taskId, userId);

        taskRepository.delete(task);
    }

    public PageDTO<TaskResponseDTO> getByUserId(UUID userId, Integer page, Integer pageSize) {
        User user = getUserOrThrow(userId);
        Pageable pageRequest = PageRequest.of(page, pageSize);

        Page<Task> taskPage = taskRepository.findByUser(user, pageRequest);

        List<TaskResponseDTO> dtos = taskPage.getContent().stream()
                .map(mapper::toDTO)
                .toList();

        return new PageDTO<>(
                dtos,
                taskPage.getNumber(),
                taskPage.getSize(),
                taskPage.getTotalElements(),
                taskPage.getTotalPages()
        );
    }


    public ResponseEntity<TaskResponseDTO> getByIdAndUserId(UUID userId, UUID taskId) {
        User user = getUserOrThrow(userId);

        return taskRepository.findByIdAndUser(taskId, user)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Não foi encontrada tarefa para o id " + taskId));
    }

    public Task getEntityByIdAndUserId(UUID userId, UUID taskId) {
        User user = getUserOrThrow(userId);

        return taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Não foi encontrada tarefa para o id " + taskId));
    }


    private User getUserOrThrow(UUID userId) {
        return userService.getEntityById(userId);
    }

    private Patient getPatientOrThrow(UUID patientId) {
        return patientService.getEntityById(patientId);
    }

    private TaskType getTaskTypeOrThrow(UUID taskTypeId) {
        return taskTypeService.getEntityById(taskTypeId);
    }

}
