package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.TaskTypeResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class TaskTypeMapper {

    public abstract TaskType toEntity(TaskTypeRequestDTO dto);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "changeDate", ignore = true)
    public abstract void updateEntityFromDto(@Valid TaskTypeRequestDTO dto, @MappingTarget TaskType taskType);

    public abstract TaskTypeResponseDTO toDTO(TaskType taskType);

}
