package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.controller.dto.LoginRequestDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginValidator {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public User validate(LoginRequestDTO dto) {

        User user = userService.getByLogin(dto.username());

        if (user == null) {
            user = userService.getByLoginByEmail(dto.username());
        }

        if (user == null) {
            throw new OperationNotPermittedException("Usuário e/ou senha incorretos.");
        }

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new OperationNotPermittedException("Usuário e/ou senha incorretos.");
        }

        return user;

    }

}
