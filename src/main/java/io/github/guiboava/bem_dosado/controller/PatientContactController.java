package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientContactRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientContactResponseDTO;
import io.github.guiboava.bem_dosado.service.PatientContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients/{patientId}/contacts")
@RequiredArgsConstructor
public class PatientContactController implements GenericController {

    private final PatientContactService patientContactService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> createPatientContact(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientContactRequestDTO dto) {

        URI uri = generateHeaderLocation(patientContactService.save(dto, patientId));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{contactId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> updatePatientContact(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("contactId") UUID patientContactId,
            @RequestBody @Valid PatientContactRequestDTO dto) {

        patientContactService.update(dto, patientId, patientContactId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{contactId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> deletePatientContact(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("contactId") UUID patientContactId) {

        patientContactService.delete(patientId, patientContactId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<List<PatientContactResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        List<PatientContactResponseDTO> list = patientContactService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{contactId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<PatientContactResponseDTO> getByContactId(@PathVariable("patientId") UUID patientId, @PathVariable("contactId") UUID patientContactId) {

        return ResponseEntity.ok(patientContactService.getByPatientIdAndContactId(patientId, patientContactId));
    }

}
