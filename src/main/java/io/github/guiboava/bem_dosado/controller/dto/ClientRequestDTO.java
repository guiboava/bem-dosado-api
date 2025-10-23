package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(name = "Cliente")
public record ClientRequestDTO(

        @NotNull
        @Length(max = 150)
        @Schema(name = "idCliente")
        String clientId,

        @NotNull
        @Length(max = 400)
        @Schema(name = "secretCliente")
        String clientSecret,

        @NotNull
        @Length(max = 200)
        @Schema(name = "linkRedirecionamento")
        String redirectURI,

        @Length(max = 50)
        @Schema(name = "scope")
        String scope
) {
}
