package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.EntityInUseException;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validate(User user) {

        duplicateRegister(user);
        validateUserType(user.getUserType());

    }

    private void duplicateRegister(User user) {
        if (user.getId() == null) {
            if (userRepository.existsByLogin(user.getLogin())) {
                throw new DuplicateRegisterException("Já existe um usuário com este nome de usuário.");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateRegisterException("Já existe um usuário com este email.");
            }
            if (userRepository.existsByCpf(user.getCpf())) {
                throw new DuplicateRegisterException("Já existe um usuário com este CPF.");
            }
        } else {
            if (userRepository.existsByLoginAndIdNot(user.getLogin(), user.getId())) {
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
            throw new EntityInUseException(String.format("Não foi possivel deletar o usuario %s, o mesmo tem vinculo com %d paciente%s.", user.getName(), (long) user.getPatients().size(), (long) user.getPatients().size() > 1 ? "s" : ""));
        }
    }

    public void validateUserType(UserType userType) {
        if (userType == UserType.A) {
            throw new OperationNotPermittedException("Você não tem permissão para criar este tipo de usuario");
        }

        if (userType != UserType.F && userType != UserType.C) {
            throw new OperationNotPermittedException
                    ("Tipo de usuário inválido, deve ser Familiar ou Cuidador");
        }
    }

}
