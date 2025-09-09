package io.github.guiboava.bem_dosado.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublicEmergencyRequestDTO(
        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(max = 100, message = "O nome do serviço não pode ter mais de 100 caracteres")
        String serviceName,

        @NotBlank(message = "O número de telefone é obrigatório")
        String phoneNumber,

        @Size(max = 1000, message = "A descrição não pode ter mais de 1000 caracteres")
        String description
) {}
