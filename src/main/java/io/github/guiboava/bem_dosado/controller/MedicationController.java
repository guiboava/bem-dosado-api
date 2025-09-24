package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.MedicationRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.MedicationResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.MedicationMapper;
import io.github.guiboava.bem_dosado.entity.model.Medication;
import io.github.guiboava.bem_dosado.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController implements GenericController {

    private final MedicationService service;
    private final MedicationMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createMedication(@RequestBody @Valid MedicationRequestDTO dto) {

        Medication medication = mapper.toEntity(dto);
        service.save(medication);

        URI uri = generateHeaderLocation(medication.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{medicationId}")
    public ResponseEntity<Void> updateMedication(
            @PathVariable("medicationId") UUID medicationId,
            @RequestBody @Valid MedicationRequestDTO dto) {

        service.update(medicationId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{medicationId}")
    public ResponseEntity<Void> deleteMedication(@PathVariable("medicationId") UUID medicationId) {

        service.delete(medicationId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponseDTO>> getAllMedication() {

        List<MedicationResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{medicationId}")
    public ResponseEntity<MedicationResponseDTO> getByMedicationId(@PathVariable("medicationId") UUID medicationId) {
        return ResponseEntity.ok(service.getByIdDTO(medicationId));
    }

}
