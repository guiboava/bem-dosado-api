package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Schema(name = "Usuário")
public record UserRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        @Schema(name = "nome")
        String name,

        @NotBlank(message = "O login de usuário é obrigatório.")
        @Size(min = 5, max = 50, message = "O login de usuário deve ter entre 5 e 50 caracteres.")
        @Schema(name = "login")
        String login,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        @Schema(name = "email")
        String email,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "CPF inválido")
        @Schema(name = "cpf")
        String cpf,

        @NotNull(message = "O tipo de usuário é obrigatório.")
        @Schema(name = "tipoUsuario")
        UserType userType,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        @Schema(name = "senha")
        String password,

        @NotBlank(message = "A confirmação de senha é obrigatória.")
        @Schema(name = "confirmacaoSenha")
        String confirmPassword,

        @NotNull(message = "O gênero é obrigatório.")
        @Schema(name = "genero")
        Gender gender,

        @NotBlank(message = "O número de telefone é obrigatório.")
        @Schema(name = "numeroTelefone")
        String phoneNumber,

        @NotBlank(message = "O CEP é obrigatório.")
        @Schema(name = "cep")
        String cep,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve estar no passado.")
        @Schema(name = "dataAniversario")
        LocalDate birthDate,

        @Schema(name = "foto64")
        String base64Image
) {
    @Schema(hidden = true)
    @AssertTrue(message = "As senhas não coincidem")
    public boolean isPasswordsMatching() {
        return password != null && password.equals(confirmPassword);
    }

}
