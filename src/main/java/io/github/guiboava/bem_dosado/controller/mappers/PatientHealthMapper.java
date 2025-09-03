package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class PatientHealthMapper {

    public abstract PatientHealth toEntity(PatientHealthRequestDTO dto);

    public abstract void updateEntityFromDto(PatientHealthRequestDTO dto, @MappingTarget PatientHealth patientHealth);

    public abstract PatientHealthResponseDTO toDTO(PatientHealth patientHealth);

}

