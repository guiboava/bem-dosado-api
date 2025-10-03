package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PatientContactRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientContactResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.util.NumberUtils;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = NumberUtils.class)
public abstract class PatientContactMapper {

    @Mapping(target = "phoneNumber", qualifiedByName = "onlyDigits")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    public abstract PatientContact toEntity(PatientContactRequestDTO dto);

    @Mapping(target = "phoneNumber", qualifiedByName = "onlyDigits")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    public abstract void updateEntityFromDto(@Valid  PatientContactRequestDTO dto, @MappingTarget PatientContact patientContact);

    public abstract PatientContactResponseDTO toDTO(PatientContact patientContact);

}

