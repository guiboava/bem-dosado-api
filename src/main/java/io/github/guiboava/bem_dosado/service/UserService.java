package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.UserMapper;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import io.github.guiboava.bem_dosado.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserValidator validator;
    private final UserMapper mapper;

    public UUID save(UserRequestDTO dto) {

        User user = mapper.toEntity(dto);
        validator.validate(user);
        return repository.save(user).getId();
    }

    public void update(UUID userId, UserRequestDTO dto) {

        User user = getEntityById(userId);

        mapper.updateEntityFromDto(dto, user);
        validator.validate(user);
        repository.save(user);
    }

    public void delete(UUID userId) {

        User user = getEntityById(userId);
        validator.validateNotLinkedToPatients(user);
        repository.delete(user);
    }

    public UserResponseDTO getById(UUID userId) {

        return repository.findById(userId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum dado de usuário para o paciente."));
    }

    public User getEntityById(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado para o id " + userId));

    }

    public List<UserResponseDTO> searchByExample(String name,
                                                 String userName,
                                                 String email,
                                                 String cpf,
                                                 UserType userType,
                                                 Gender gender,
                                                 String phoneNumber,
                                                 LocalDate birthDate) {

        var user = new User();
        user.setName(name);
        user.setUserName(userName);
        user.setEmail(email);
        user.setCpf(cpf);
        user.setUserType(userType);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> userExample = Example.of(user, matcher);
        return repository.findAll(userExample).stream()
                .map(mapper::toDTO)
                .toList();

    }
}
