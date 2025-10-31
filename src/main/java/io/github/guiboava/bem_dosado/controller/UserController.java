package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.UserPatchRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuário")
@Slf4j
public class UserController implements GenericController {

    private final UserService service;

    @PostMapping
    @Operation(summary = "Salvar.", description = "Criar um novo usuário dentro do sistema.")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDTO dto) {

        log.info("Cadastrando um novo usuário: {}", dto.name());

        URI uri = generateHeaderLocation(service.save(dto));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar.", description = "Atualizar um usuário dentro do sistema.")
    public ResponseEntity<Void> updateUser(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserRequestDTO dto) {
        log.info("Atualizando o usuário de id: {}", user.getId().toString());

        service.update(user, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar Imagem.", description = "Atualizar a imagem de um usuário dentro do sistema.")
    public ResponseEntity<Void> patchUser(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserPatchRequestDTO dto) {
        log.info("Atualizado a imagem do usuário: {}", user.getId().toString());

        service.patchUpdate(user, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar.", description = "Deletar um usuário dentro do sistema.")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {

        log.info("Deletando usuário de id: {}", userId.toString());

        service.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Pesquisar.", description = "Pesquisar dinâmica por usuários dentro do sistema.")
    public ResponseEntity<List<UserResponseDTO>> searchUser(@RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "login", required = false) String login,
                                                            @RequestParam(value = "email", required = false) String email,
                                                            @RequestParam(value = "cpf", required = false) String cpf,
                                                            @RequestParam(value = "userType", required = false) UserType userType,
                                                            @RequestParam(value = "gender", required = false) Gender gender,
                                                            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                            @RequestParam(value = "birthDate", required = false) LocalDate birthDate
    ) {
        return ResponseEntity.ok(service.searchByExample(name, login, email, cpf, userType, gender, phoneNumber, birthDate));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Encontrar.", description = "Pesquisar por um usuário dentro do sistema.")
    public ResponseEntity<UserResponseDTO> getByUserId(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(service.getByUser(user));
    }

}
