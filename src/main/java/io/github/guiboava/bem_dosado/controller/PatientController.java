package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.service.PatientService;
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
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController implements GenericController {

    private final PatientService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> createPatient(@RequestBody @Valid PatientRequestDTO dto) {

        URI uri = generateHeaderLocation(service.save(dto));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> updatePatient(@PathVariable UUID patientId, @RequestBody @Valid PatientRequestDTO dto) {

        service.update(patientId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<Void> deletePatient(@PathVariable("patientId") UUID patientId) {

        service.delete(patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<List<PatientResponseDTO>> searchPatient(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "cpf", required = false) String cpf, @RequestParam(value = "birthDate", required = false) LocalDate birthDate, @RequestParam(value = "gender", required = false) Gender gender, @RequestParam(value = "cep", required = false) String cep, @RequestParam(value = "dependency", required = false) Dependency dependency, @RequestParam(value = "healthPlan", required = false) String healthPlan, @RequestParam(value = "cardNumber", required = false) String cardNumber, @RequestParam(value = "allergies", required = false) String allergies, @RequestParam(value = "medications", required = false) String medicationsDescription, @RequestParam(value = "note", required = false) String note) {

        return ResponseEntity.ok(service.searchByExample(name, cpf, birthDate, gender, cep, dependency, healthPlan, cardNumber, allergies, medicationsDescription, note));

    }

    @GetMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    public ResponseEntity<PatientResponseDTO> getByPatientId(@PathVariable("patientId") UUID patientId) {

        return ResponseEntity.ok(service.getById(patientId));

    }

}
