package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.service.PatientHealthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients/{patientId}/healths")
@RequiredArgsConstructor
public class PatientHealthController implements GenericController {

    private final PatientHealthService patientHealthService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> createPatientHealth(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientHealthRequestDTO dto) {

        URI uri = generateHeaderLocation(patientHealthService.save(dto, patientId));

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{healthId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> updatePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId,
            @RequestBody @Valid PatientHealthRequestDTO dto) {

        patientHealthService.update(dto, patientId, healthId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{healthId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER')")
    public ResponseEntity<Void> deletePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId) {

        patientHealthService.delete(patientId, healthId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<List<PatientHealthResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        List<PatientHealthResponseDTO> list = patientHealthService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{healthId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<PatientHealthResponseDTO> getByHealthId(@PathVariable("patientId") UUID patientId, @PathVariable("healthId") UUID healthId) {

        return ResponseEntity.ok(patientHealthService.getByPatientIdAndHealthId(patientId, healthId));
    }

}
