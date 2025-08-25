package io.github.guiboava.bem_dosado.controller.dto;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;

import java.time.LocalDate;

public record UserRequestDTO(String name,
                             String userName,
                             String email,
                             String cpf,
                             UserType userType,
                             String password,
                             String confirmPassword,
                             Gender gender,
                             String phoneNumber,
                             String cep,
                             LocalDate birthDate) {
    public User createUser() {
        User user = new User();
        user.setName(name);
        user.setUserName(userName);
        user.setEmail(email);
        user.setCpf(cpf);
        user.setUserType(userType);
        user.setPassword(password);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setCep(cep);
        user.setBirthDate(birthDate);
        return user;
    }
}
