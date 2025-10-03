package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String name,
                              String login,
                              String email,
                              String cpf,
                              UserType userType,
                              Gender gender,
                              String phoneNumber,
                              String cep,
                              LocalDate birthDate) {
}
