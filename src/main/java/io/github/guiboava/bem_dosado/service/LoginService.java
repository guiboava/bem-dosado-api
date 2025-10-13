package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.LoginRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.LoginResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.validator.LoginValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginValidator validator;
    private final JwtService jwtService;


    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {

        User user = validator.validate(dto);

        String token = jwtService.generateToken(user.getLogin());
        String refreshToken = jwtService.generateRefreshToken(user.getLogin());
        long expiresIn = jwtService.getExpirationSeconds();

        LoginResponseDTO responseDto = new LoginResponseDTO(token, refreshToken, user.getLogin(), user.getId(), user.getEmail(), expiresIn);


        return ResponseEntity.ok(responseDto);

    }

}
