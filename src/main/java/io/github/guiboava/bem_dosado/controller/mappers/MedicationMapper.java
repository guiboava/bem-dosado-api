package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.MedicationRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.MedicationResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Medication;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MedicationMapper {

    public abstract Medication toEntity(MedicationRequestDTO dto);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    public abstract void updateEntityFromDto(@Valid MedicationRequestDTO dto, @MappingTarget Medication medication);

    public abstract MedicationResponseDTO toDTO(Medication medication);

}
