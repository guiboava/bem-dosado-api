package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.EntityInUseException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validate(User user) {

        duplicateRegister(user);

    }

    private void duplicateRegister(User user) {
        if (user.getId() == null) {
            if (userRepository.existsByUserName(user.getUserName())) {
                throw new DuplicateRegisterException("Já existe um usuário com este nome de usuário.");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateRegisterException("Já existe um usuário com este email.");
            }
            if (userRepository.existsByCpf(user.getCpf())) {
                throw new DuplicateRegisterException("Já existe um usuário com este CPF.");
            }
        } else {
            if (userRepository.existsByUserNameAndIdNot(user.getUserName(), user.getId())) {
                throw new DuplicateRegisterException("Já existe um usuário com este nome de usuário.");
            }
            if (userRepository.existsByEmailAndIdNot(user.getEmail(), user.getId())) {
                throw new DuplicateRegisterException("Já existe um usuário com este email.");
            }
            if (userRepository.existsByCpfAndIdNot(user.getCpf(), user.getId())) {
                throw new DuplicateRegisterException("Já existe um usuário com este CPF.");
            }
        }
    }

    public void validateNotLinkedToPatients(User user) {
        if (!user.getPatients().isEmpty()) {
            throw new EntityInUseException("Usuario vinculado a pacientes não pode ser deletado.");
        }
    }

}
