package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 caracteres")
        String name,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "CPF inválido")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate birthDate,

        @NotNull(message = "O gênero é obrigatório")
        Gender gender,

        @NotBlank(message = "O CEP é obrigatório")
        String cep,

        @NotNull(message = "O nível de dependência é obrigatório")
        Dependency dependency,


        @Size(max = 50, message = "O plano de saúde deve ter no máximo 50 caracteres")
        String healthPlan,

        @Size(max = 20, message = "O número do cartão deve ter no máximo 20 caracteres")
        String cardNumber,

        @Size(max = 500, message = "As alergias devem ter no máximo 500 caracteres")
        String allergies,

        @Size(max = 500, message = "Os medicamentos devem ter no máximo 500 caracteres")
        String medications,

        @Size(max = 1000, message = "A observação deve ter no máximo 1000 caracteres")
        String note) {
}
