package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientContactRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientContactResponseDTO;
import io.github.guiboava.bem_dosado.service.PatientContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Contato de Paciente")
public class PatientContactController implements GenericController {

    private final PatientContactService patientContactService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Salvar", description = "Criar um novo contato de paciente dentro do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Inautorizado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    public ResponseEntity<Void> createPatientContact(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientContactRequestDTO dto) {

        URI uri = generateHeaderLocation(patientContactService.save(dto, patientId));
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{contactId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar.", description = "Atualizar um contato de paciente dentro do sistema.")
    public ResponseEntity<Void> updatePatientContact(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("contactId") UUID patientContactId,
            @RequestBody @Valid PatientContactRequestDTO dto) {

        patientContactService.update(dto, patientId, patientContactId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{contactId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Deletar.", description = "Deletar um contato de paciente dentro do sistema.")
    public ResponseEntity<Void> deletePatientContact(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("contactId") UUID patientContactId) {

        patientContactService.delete(patientId, patientContactId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Listar contato de paciente por paciente dentro do sistema.")
    public ResponseEntity<List<PatientContactResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        List<PatientContactResponseDTO> list = patientContactService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{contactId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Encontrar.", description = "Pesquisar por um contato de paciente por paciente dentro do sistema.")
    public ResponseEntity<PatientContactResponseDTO> getByContactId(@PathVariable("patientId") UUID patientId, @PathVariable("contactId") UUID patientContactId) {

        return ResponseEntity.ok(patientContactService.getByPatientIdAndContactId(patientId, patientContactId));
    }

}
