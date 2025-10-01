package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/patients")
public class UserPatientController {

    private final UserService userService;

    public UserPatientController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<Void> addPatient(
            @PathVariable UUID userId,
            @PathVariable UUID patientId) {
        userService.addPatientToUser(userId, patientId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> removePatient(
            @PathVariable UUID userId,
            @PathVariable UUID patientId) {
        userService.removePatientFromUser(userId, patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> listPatients(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserPatients(userId));
    }
}

