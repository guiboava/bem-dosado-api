package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/patients")
public class UserPatientController {

    private final UserService userService;

    public UserPatientController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> addPatient(
            @AuthenticationPrincipal User user,
            @PathVariable UUID patientId) {
        userService.addPatientToUser(user.getId(), patientId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> removePatient(
            @AuthenticationPrincipal User user,
            @PathVariable UUID patientId) {
        userService.removePatientFromUser(user.getId(), patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<List<PatientResponseDTO>> listPatients(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserPatients(user.getId()));
    }
}

