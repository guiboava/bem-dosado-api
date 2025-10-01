package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.service.PatientHealthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createPatientHealth(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientHealthRequestDTO dto) {

        URI uri = generateHeaderLocation(patientHealthService.save(dto, patientId));

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{healthId}")
    public ResponseEntity<Void> updatePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId,
            @RequestBody @Valid PatientHealthRequestDTO dto) {

        patientHealthService.update(dto, patientId, healthId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{healthId}")
    public ResponseEntity<Void> deletePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId) {

        patientHealthService.delete(patientId, healthId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PatientHealthResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        List<PatientHealthResponseDTO> list = patientHealthService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{healthId}")
    public ResponseEntity<PatientHealthResponseDTO> getByHealthId(@PathVariable("patientId") UUID patientId, @PathVariable("healthId") UUID healthId) {

        return ResponseEntity.ok(patientHealthService.getByPatientIdAndHealthId(patientId, healthId));
    }

}
