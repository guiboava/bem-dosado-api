package io.github.guiboava.bem_dosado.security;

import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = service.getByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getUserType().getRoleName())
                .build();
    }
}
