package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientContactRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientContactResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientContactMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.service.PatientContactService;
import io.github.guiboava.bem_dosado.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients/{patientId}/contacts")
@RequiredArgsConstructor
public class PatientContactController implements GenericController {

    private final PatientContactService patientContactService;
    private final PatientService patientService;
    private final PatientContactMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createPatientContact(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientContactRequestDTO dto) {
        Patient patient = getPatientOrThrow(patientId);
        PatientContact patientContact = mapper.toEntity(dto);

        patientContact.setPatient(patient);
        patientContactService.save(patientContact);

        URI uri = generateHeaderLocation(patientContact.getId());

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Void> updatePatientContact(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("contactId") UUID contactId,
            @RequestBody @Valid PatientContactRequestDTO dto) {

        Patient patient = getPatientOrThrow(patientId);

        PatientContact patientContact = patientContactService.getByPatientIdAndContactId(patientId, contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de contato para o id " + contactId));

        mapper.updateEntityFromDto(dto, patientContact);
        patientContactService.update(patientContact);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deletePatientContact(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("contactId") UUID contactId) {

        Patient patient = getPatientOrThrow(patientId);

        PatientContact patientContact = patientContactService
                .getByPatientIdAndContactId(patientId, contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de contato para o id " + contactId));

        patientContactService.delete(patientContact);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PatientContactResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        Patient patient = getPatientOrThrow(patientId);

        List<PatientContactResponseDTO> list = patientContactService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<PatientContactResponseDTO> getByContactId(@PathVariable("patientId") UUID patientId, @PathVariable("contactId") UUID contactId) {

        return patientContactService.getByPatientIdAndContactId(patientId, contactId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dado de contato para o id " + contactId));
    }

    private Patient getPatientOrThrow(UUID patientId) {
        return patientService.getById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
    }

}
