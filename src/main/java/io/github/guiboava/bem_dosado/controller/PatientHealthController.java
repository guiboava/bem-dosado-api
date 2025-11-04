package io.github.guiboava.bem_dosado.controller;


import io.github.guiboava.bem_dosado.controller.dto.PatientHealthRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.service.PatientHealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients/{patientId}/healths")
@RequiredArgsConstructor
@Tag(name = "Saúde Paciente")
@Slf4j
public class PatientHealthController implements GenericController {

    private final PatientHealthService patientHealthService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Salvar", description = "Criar um novo dado de saúde de paciente dentro do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Inautorizado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    public ResponseEntity<Void> createPatientHealth(@PathVariable("patientId") UUID patientId, @RequestBody @Valid PatientHealthRequestDTO dto) {

        log.info("Cadastrando um novo cadastro de saúde para o paciente de id: {}", patientId);

        URI uri = generateHeaderLocation(patientHealthService.save(dto, patientId));

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{healthId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar.", description = "Atualizar um dado de saúde de paciente dentro do sistema.")
    public ResponseEntity<Void> updatePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId,
            @RequestBody @Valid PatientHealthRequestDTO dto) {

        log.info("Atualizando um cadastro de saúde para o paciente de id: {}", patientId);

        patientHealthService.update(dto, patientId, healthId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{healthId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Deletar.", description = "Deletar um dado de saúde de paciente dentro do sistema.")
    public ResponseEntity<Void> deletePatientHealth(
            @PathVariable("patientId") UUID patientId,
            @PathVariable("healthId") UUID healthId) {

        log.info("Deletando um cadastro de saúde para o paciente de id: {}", patientId);

        patientHealthService.delete(patientId, healthId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Listar dados de saúde de paciente dentro do sistema.")
    public ResponseEntity<List<PatientHealthResponseDTO>> getAllByPatientId(@PathVariable("patientId") UUID patientId) {

        List<PatientHealthResponseDTO> list = patientHealthService.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{healthId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Pesquisar um dado de saúde de paciente dentro do sistema.")
    public ResponseEntity<PatientHealthResponseDTO> getByHealthId(@PathVariable("patientId") UUID patientId, @PathVariable("healthId") UUID healthId) {

        return ResponseEntity.ok(patientHealthService.getByPatientIdAndHealthId(patientId, healthId));
    }

}
