package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.ErrorResponse;
import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDTO user) {
        try {
            var userEntity = user.createUser();
            userService.save(userEntity);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userEntity.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (DuplicateRegisterException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> searchUser() {
        List<User> getUsersList = userService.getUsers();
        List<UserResponseDTO> list = getUsersList.stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getEmail(),
                user.getCpf(),
                user.getUserType(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getCep(),
                user.getBirthDate())).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    //TESTAR
    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getByUserId(@PathVariable("id") String id) {
        // ADD TRY CATCH
        UUID userId = UUID.fromString(id);
        Optional<User> userOptional = userService.getById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO dto = new UserResponseDTO(user.getId(),
                    user.getName(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getUserType(),
                    user.getGender(),
                    user.getPhoneNumber(),
                    user.getCep(),
                    user.getBirthDate());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") String id) {
        try {
            UUID userId = UUID.fromString(id);
            Optional<User> optionalUser = userService.getById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            userService.delete(optionalUser.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotPermittedException e) {
            var errorResponse = ErrorResponse.defaultError(e.getMessage());
            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") String id, @RequestBody UserRequestDTO dto) {
        try {
            UUID userId = UUID.fromString(id);
            Optional<User> userOptional = userService.getById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var user = userOptional.get();
            user.setName(dto.name());
            user.setUserName(dto.userName());
            user.setEmail(dto.email());
            user.setCpf(dto.cpf());
            user.setUserType(dto.userType());
            user.setPassword(dto.password());
            user.setGender(dto.gender());
            user.setPhoneNumber(dto.phoneNumber());
            user.setCep(dto.cep());
            user.setBirthDate(dto.birthDate());

            userService.update(user);

            return ResponseEntity.noContent().build();
        } catch (DuplicateRegisterException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

}
