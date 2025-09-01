package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class PatientHealthMapper {

    public abstract PatientHealth toEntity(PatientHealth dto);

    public abstract void updateEntityFromDto(PatientRequestDTO dto, @MappingTarget Patient patient);

    public abstract PatientResponseDTO toDTO(Patient patient);

}
