package io.github.guiboava.bem_dosado.security;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication.getUser();
        }
        return null;
    }

}
