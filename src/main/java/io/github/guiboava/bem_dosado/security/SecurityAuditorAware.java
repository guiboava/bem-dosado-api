package io.github.guiboava.bem_dosado.security;

import io.github.guiboava.bem_dosado.entity.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<User> {

    private final SecurityService securityService;

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(securityService.getLoggedUser());
    }
}
