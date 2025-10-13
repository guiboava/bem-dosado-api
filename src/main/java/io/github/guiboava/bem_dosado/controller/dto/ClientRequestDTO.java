package io.github.guiboava.bem_dosado.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientRequestDTO {

    @NotNull
    @Length(max = 150)
    private String clientId;

    @NotNull
    @Length(max = 400)
    private String clientSecret;

    @NotNull
    @Length(max = 200)
    private String redirectURI;

    @Length(max = 50)
    private String scope;
}
