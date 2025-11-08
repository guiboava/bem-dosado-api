package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import io.github.guiboava.bem_dosado.repository.TaskTypeRepository;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, TaskType.class, MedicationMapper.class})
public abstract class TaskMapper {

    @Autowired
    PatientRepository patientRepository;
    TaskTypeRepository taskTypeRepository;

    @Mapping(target = "patient", expression = "java( patientRepository.findById(dto.patientId()).orElse(null) )")
    public abstract Task toEntity(TaskRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "describe", source = "dto.describe")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "updatedByUser", ignore = true)
    @Mapping(target = "patient", source = "patient")
    @Mapping(target = "taskType", source = "taskType")
    public abstract void updateEntityFromDto(@Valid TaskRequestDTO dto, Patient patient, TaskType taskType, @MappingTarget Task task);


    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "taskTypeId", source = "taskType.id")
    @Mapping(target = "medications", source = "medications")
    public abstract TaskResponseDTO toDTO(Task task);


    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "taskTypeId", source = "taskType.id")
    @Mapping(target = "medicationsIds", expression = "java(task.getMedications().stream().map(Medication::getId).collect(java.util.stream.Collectors.toSet()))"
    )
    public abstract TaskRequestDTO toRequestDTO(Task task);


}