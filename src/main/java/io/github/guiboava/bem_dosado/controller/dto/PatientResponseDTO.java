package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record PatientResponseDTO(UUID id,
                                 String name,
                                 String cpf,
                                 LocalDate birthDate,
                                 Gender gender,
                                 String cep,
                                 Dependency dependency,
                                 String healthPlan,
                                 String cardNumber,
                                 String allergies,
                                 String medicationsDescription,
                                 String note,
                                 String base64Image) {
}


