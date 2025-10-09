package io.github.guiboava.bem_dosado.security;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        User findedUser = service.getByLogin(login);

        if (findedUser == null) {
            throw getErrorUserNotFound();
        }

        String criptPassword = findedUser.getPassword();

        boolean matchesPassword = encoder.matches(password, criptPassword);

        if (matchesPassword) {
            return new CustomAuthentication(findedUser);
        }

        throw getErrorUserNotFound();
    }

    private UsernameNotFoundException getErrorUserNotFound() {
        return new UsernameNotFoundException("Usuario e/ou senha incorreto");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
