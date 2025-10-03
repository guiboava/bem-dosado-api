package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.security.SecurityService;
import io.github.guiboava.bem_dosado.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDTO dto) {

        URI uri = generateHeaderLocation(service.save(dto));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Valid UserRequestDTO dto) {

        service.update(userId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {

        service.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
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

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<UserResponseDTO> getByUserId(@PathVariable("userId") UUID userId) {

        return ResponseEntity.ok(service.getById(userId));
    }

}
