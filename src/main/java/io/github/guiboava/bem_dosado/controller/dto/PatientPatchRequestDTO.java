package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Patch Paciente")
public record PatientPatchRequestDTO(

        @Schema(name = "foto64")
        String base64Image
) {

}
