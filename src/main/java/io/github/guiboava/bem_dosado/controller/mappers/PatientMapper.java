package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.util.NumberUtils;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = NumberUtils.class)
public abstract class PatientMapper {

    @Mapping(target = "cep", qualifiedByName = "onlyDigits")
    @Mapping(target = "cpf", qualifiedByName = "onlyDigits")
    public abstract Patient toEntity(PatientRequestDTO dto);

    @Mapping(target = "cep", qualifiedByName = "onlyDigits")
    @Mapping(target = "cpf", qualifiedByName = "onlyDigits")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    public abstract void updateEntityFromDto(@Valid PatientRequestDTO dto, @MappingTarget Patient patient);

    @Mapping(target = "medicationsDescription", source = "medicationsDescription")
    public abstract PatientResponseDTO toDTO(Patient patient);

}
 