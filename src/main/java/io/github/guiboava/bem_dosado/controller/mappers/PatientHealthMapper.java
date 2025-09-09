package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class PatientHealthMapper {

    public abstract PatientHealth toEntity(PatientHealthRequestDTO dto);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "changeDate", ignore = true)
    public abstract void updateEntityFromDto(@Valid PatientHealthRequestDTO dto, @MappingTarget PatientHealth patientHealth);

    public abstract PatientHealthResponseDTO toDTO(PatientHealth patientHealth);

}

