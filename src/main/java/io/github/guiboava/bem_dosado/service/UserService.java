package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import io.github.guiboava.bem_dosado.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserValidator validator;

    public User save(User user) {
        validator.validate(user);
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public Optional<User> getById(UUID id) {

        return repository.findById(id);
    }

    public void delete(User user) {
        //Validar Relações futuramente.
        repository.delete(user);
    }

    public void update(User user) {
        if (user.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de usuario é nescessário que o usuario esteja salvo na base.");
        }
        validator.validate(user);
        repository.save(user);
    }

    public List<User> searchByExample(String name,
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
        return repository.findAll(userExample);

    }
}
