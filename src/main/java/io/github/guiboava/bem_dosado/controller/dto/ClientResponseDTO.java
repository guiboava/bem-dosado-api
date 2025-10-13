package io.github.guiboava.bem_dosado.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientResponseDTO {

    private UUID id;
    private String clientId;
    private String redirectURI;
    private String scope;
}
