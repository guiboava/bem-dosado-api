package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Resposta Campo Erro", hidden = true)
public record ErrorField(@Schema(hidden = true)
                         String field,
                         @Schema(hidden = true)
                         String error) {
}
