package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Schema(name = "Paciente")
public record PatientRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 caracteres")
        @Schema(name = "nome")
        String name,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "CPF inválido")
        @Schema(name = "cpf")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Schema(name = "dataNascimento")
        LocalDate birthDate,

        @NotNull(message = "O gênero é obrigatório")
        @Schema(name = "genero")
        Gender gender,

        @NotBlank(message = "O CEP é obrigatório")
        @Schema(name = "cep")
        String cep,

        @NotNull(message = "O nível de dependência é obrigatório")
        @Schema(name = "dependencia")
        Dependency dependency,

        @Size(max = 50, message = "O plano de saúde deve ter no máximo 50 caracteres")
        @Schema(name = "planoDeSaude")
        String healthPlan,

        @Size(max = 20, message = "O número do cartão deve ter no máximo 20 caracteres")
        @Schema(name = "numeroCartaoSaude")
        String cardNumber,

        @Size(max = 500, message = "As alergias devem ter no máximo 500 caracteres")
        @Schema(name = "alergias")
        String allergies,

        @Size(max = 500, message = "A descrição dos medicamentos devem ter no máximo 500 caracteres")
        @Schema(name = "descricaoMedicacao")
        String medicationsDescription,

        @Size(max = 1000, message = "A observação deve ter no máximo 1000 caracteres")
        @Schema(name = "notas")
        String note) {
}
