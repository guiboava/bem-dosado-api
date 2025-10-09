package io.github.guiboava.bem_dosado.security;

import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.mappers.UserMapper;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final UserMapper mapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        User user = userService.getByEmail(email);

        if (user == null) {
            user = createUser(oAuth2User);
        }

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User createUser(OAuth2User oAuth2User) {
        User user = new User();
        user.setName(oAuth2User.getAttribute("name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setLogin(getLoginWithEmail(user.getEmail()));
        user.setPassword(UUID.randomUUID().toString());
        user.setCpf("00000000000");
        user.setUserType(UserType.C);
        user.setGender(Gender.O);
        user.setPhoneNumber("(00)00000-0000");
        user.setCep("00000-000");
        user.setBirthDate(LocalDate.of(2000, 1, 1));

        UserRequestDTO dto = mapper.toRequestDTO(user);

        userService.save(dto);
        return user;
    }

    private String getLoginWithEmail(String email) {

        return email.substring(0, email.indexOf("@"));

    }
}
