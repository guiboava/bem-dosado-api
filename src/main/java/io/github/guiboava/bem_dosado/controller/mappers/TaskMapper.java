package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.TaskRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import io.github.guiboava.bem_dosado.repository.TaskTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, TaskType.class})
public abstract class TaskMapper {

    @Autowired
    PatientRepository patientRepository;
    TaskTypeRepository taskTypeRepository;

    @Mapping(target = "patient", expression = "java( patientRepository.findById(dto.patientId()).orElse(null) )")
    public abstract Task toEntity(TaskRequestDTO dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "describe", source = "dto.describe")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "changeDate", ignore = true)
    @Mapping(target = "patient", source = "patient")
    @Mapping(target = "taskType", source = "taskType")
    public abstract void updateEntityFromDto(TaskRequestDTO dto, Patient patient, TaskType taskType, @MappingTarget Task task);


    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "taskTypeId", source = "taskType.id")
    public abstract TaskResponseDTO toDTO(Task task);
}