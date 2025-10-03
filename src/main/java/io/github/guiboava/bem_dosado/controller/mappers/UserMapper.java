package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.util.NumberUtils;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = NumberUtils.class)
public abstract class UserMapper {

    @Mapping(target = "phoneNumber", qualifiedByName = "onlyDigits")
    @Mapping(target = "cep", qualifiedByName = "onlyDigits")
    @Mapping(target = "cpf", qualifiedByName = "onlyDigits")
    public abstract User toEntity(UserRequestDTO dto);

    @Mapping(target = "phoneNumber", qualifiedByName = "onlyDigits")
    @Mapping(target = "cep", qualifiedByName = "onlyDigits")
    @Mapping(target = "cpf", qualifiedByName = "onlyDigits")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updated_date", ignore = true)
    public abstract void updateEntityFromDto(@Valid UserRequestDTO dto,@MappingTarget User user);

    public abstract UserResponseDTO toDTO(User user);

}