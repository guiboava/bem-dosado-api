package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Schema(name = "Contato Paciente")
public record PatientContactRequestDTO(
        @NotBlank(message = "O nome não pode estar vazio")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        @Schema(name = "nome")
        String name,

        @NotBlank(message = "O telefone não pode estar vazio")
        @Size(min = 10, max = 15)
        @Schema(name = "numeroTelefone")
        String phoneNumber,

        @Size(max = 50, message = "A afiliação deve ter no máximo 50 caracteres")
        @Schema(name = "afiliacao")
        String affiliation) {
}
