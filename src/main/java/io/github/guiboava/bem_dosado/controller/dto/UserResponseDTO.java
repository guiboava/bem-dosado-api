package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String name,
                              String userName,
                              String email,
                              String cpf,
                              UserType userType,
                              Gender gender,
                              String phoneNumber,
                              String cep,
                              LocalDate birthDate) {
    public User createUser() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUserName(userName);
        user.setEmail(email);
        user.setCpf(cpf);
        user.setUserType(userType);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setCep(cep);
        user.setBirthDate(birthDate);
        return user;
    }
}
