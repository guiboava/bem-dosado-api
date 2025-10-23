package io.github.guiboava.bem_dosado.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Contato de Emergência Pública")
public record PublicEmergencyRequestDTO(
        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(max = 100, message = "O nome do serviço não pode ter mais de 100 caracteres")
        @Schema(name = "nomeServico")
        String serviceName,

        @NotBlank(message = "O número de telefone é obrigatório")
        @Schema(name = "numeroServico")
        String phoneNumber,

        @Size(max = 1000, message = "A descrição não pode ter mais de 1000 caracteres")
        @Schema(name = "descricao")
        String description
) {}
