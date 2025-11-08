package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.PublicEmergency;
import io.github.guiboava.bem_dosado.util.NumberUtils;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = NumberUtils.class)
public abstract class PublicEmergencyMapper {

    @Mapping(target = "phoneNumber", qualifiedByName = "onlyDigits")
    public abstract PublicEmergency toEntity(PublicEmergencyRequestDTO dto);

    @Mapping(target = "phoneNumber", qualifiedByName = "onlyDigits")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    public abstract void updateEntityFromDto(@Valid PublicEmergencyRequestDTO dto, @MappingTarget PublicEmergency publicEmergency);

    public abstract PublicEmergencyResponseDTO toDTO(PublicEmergency publicEmergency);

    public abstract PublicEmergencyRequestDTO toRequestDTO(PublicEmergency publicEmergency);
}
