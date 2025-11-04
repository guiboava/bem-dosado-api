package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/patients")
@Tag(name = "Vínculos de paciente com usuário")
@Slf4j
public class UserPatientController {

    private final UserService userService;

    public UserPatientController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Víncular.", description = "Adicionar um vínculo de paciente com usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Inautorizado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    public ResponseEntity<Void> addPatient(
            @AuthenticationPrincipal User user,
            @PathVariable UUID patientId) {

        log.info("Vinculando o paciente: {}, ao usuário: {}", patientId, user.getName());

        userService.addPatientToUser(user.getId(), patientId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Desvíncular.", description = "Remover um vínculo de paciente com usuário.")
    public ResponseEntity<Void> removePatient(
            @AuthenticationPrincipal User user,
            @PathVariable UUID patientId) {

        log.info("Desvinculando o paciente: {}, do usuário: {}", patientId, user.getName());

        userService.removePatientFromUser(user.getId(), patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Mostra todos vínculos de paciente de um usuário.")
    public ResponseEntity<List<PatientResponseDTO>> listPatients(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserPatients(user.getId()));
    }
}

