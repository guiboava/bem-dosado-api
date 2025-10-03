package io.github.guiboava.bem_dosado.security;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String login;
        if (principal instanceof UserDetails userDetails) {
            login = userDetails.getUsername();
        } else if (principal instanceof String username) {
            login = username;
        } else {
            throw new IllegalStateException("Tipo de principal inesperado: " + principal.getClass());
        }

        return userService.getByLogin(login);
    }


}
