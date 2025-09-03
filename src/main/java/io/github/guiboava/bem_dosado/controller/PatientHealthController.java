package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientHealthMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.service.PatientHealthService;
import io.github.guiboava.bem_dosado.service.PatientService;
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
    private final PatientService patientService;
    private final PatientHealthMapper mapper;


    @PostMapping
    public ResponseEntity<Void> createPatientHealth(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientHealthRequestDTO dto) {
        Patient patient = patientService.getById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        PatientHealth patientHealth = mapper.toEntity(dto);

        patientHealth.setPatient(patient);
        patientHealthService.save(patientHealth);

        URI uri = generateHeaderLocation(patientHealth.getId());

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{healthId}")
    public ResponseEntity<Void> updatePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId,
            @RequestBody @Valid PatientHealthRequestDTO dto) {

        Patient patient = patientService.getById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        PatientHealth patientHealth = patientHealthService.getByPatientIdAndHealthId(patientId, healthId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + healthId));

        mapper.updateEntityFromDto(dto, patientHealth);
        patientHealthService.update(patientHealth);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{healthId}")
    public ResponseEntity<Void> deletePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId) {

        Patient patient = patientService.getById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        PatientHealth patientHealth = patientHealthService
                .getByPatientIdAndHealthId(patientId, healthId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + healthId));

        patientHealthService.delete(patientHealth);

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<PatientHealthResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        Patient patient = patientService.getById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        List<PatientHealthResponseDTO> list = patientHealthService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{healthId}")
    public ResponseEntity<PatientHealthResponseDTO> getByHealthId(@PathVariable("patientId") UUID patientId, @PathVariable("healthId") UUID healthId) {

        return patientHealthService.getByPatientIdAndHealthId(patientId, healthId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de saúde para o id " + healthId));
    }

}
