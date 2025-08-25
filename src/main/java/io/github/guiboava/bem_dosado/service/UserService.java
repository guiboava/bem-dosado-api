package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import io.github.guiboava.bem_dosado.validator.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public User save(User user) {
        userValidator.validate(user);
        return userRepository.save(user);
    }

    //IMPLEMENTAR UMA FORMA GENERICA DE USAR VARIOS PARAMETROS.
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }

    public void delete(User user) {
        //Validar Relações futuramente.
        userRepository.delete(user);
    }

    public void update(User user) {
        if (user.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de usuario é nescessário que o usuario esteja salvo na base.");
        }
        userValidator.validate(user);
        userRepository.save(user);
    }

}
