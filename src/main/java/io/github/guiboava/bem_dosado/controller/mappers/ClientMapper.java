package io.github.guiboava.bem_dosado.controller.mappers;

import io.github.guiboava.bem_dosado.controller.dto.ClientRequestDTO;
import io.github.guiboava.bem_dosado.entity.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    public abstract Client toEntity(ClientRequestDTO dto);

    public abstract ClientRequestDTO toDTO(Client client);

}
