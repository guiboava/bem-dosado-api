package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.UserMapper;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDTO user) {

        var userEntity = mapper.toEntity(user);
        service.save(userEntity);
        URI uri = generateHeaderLocation(userEntity.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> searchUser(@RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "userName", required = false) String userName,
                                                            @RequestParam(value = "email", required = false) String email,
                                                            @RequestParam(value = "cpf", required = false) String cpf,
                                                            @RequestParam(value = "userType", required = false) UserType userType,
                                                            @RequestParam(value = "gender", required = false) Gender gender,
                                                            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                            @RequestParam(value = "birthDate", required = false) LocalDate birthDate
    ) {
        List<User> searchUsers = service.searchByExample(name, userName, email, cpf, userType, gender, phoneNumber, birthDate);
        List<UserResponseDTO> list = searchUsers.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByUserId(@PathVariable("id") String id) {
        UUID userId = UUID.fromString(id);
        return service.getById(userId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum dado de usuário para o paciente."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        UUID userId = UUID.fromString(id);
        Optional<User> optionalUser = service.getById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(optionalUser.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserRequestDTO dto) {

        User user = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        mapper.updateEntityFromDto(dto, user);
        service.update(user);

        return ResponseEntity.noContent().build();
    }


}
