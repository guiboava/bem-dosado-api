package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.MedicationRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.MedicationResponseDTO;
import io.github.guiboava.bem_dosado.service.MedicationService;
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
@RequestMapping("/medications")
@RequiredArgsConstructor
@Tag(name = "Medicação")
@Slf4j
public class MedicationController implements GenericController {

    private final MedicationService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Salvar", description = "Criar uma nova medicação dentro do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Inautorizado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação.")
    })
    public ResponseEntity<Void> createMedication(@RequestBody @Valid MedicationRequestDTO dto) {

        log.info("Cadastrando uma nova medicação: {}", dto.name());

        URI uri = generateHeaderLocation(service.save(dto));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{medicationId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Atualizar.", description = "Atualizar uma medicação dentro do sistema.")
    public ResponseEntity<Void> updateMedication(
            @PathVariable("medicationId") UUID medicationId,
            @RequestBody @Valid MedicationRequestDTO dto) {

        log.info("Atualizando a medicação de id: {}", medicationId);

        service.update(medicationId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{medicationId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Deletar.", description = "Deletar uma medicação dentro do sistema.")
    public ResponseEntity<Void> deleteMedication(@PathVariable("medicationId") UUID medicationId) {

        log.info("Deletando a medicação de id: {}", medicationId);

        service.delete(medicationId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Listar.", description = "Listar medicações dentro do sistema.")
    public ResponseEntity<List<MedicationResponseDTO>> getAllMedication() {

        List<MedicationResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{medicationId}")
    @PreAuthorize("hasAnyRole('ADMIN','CAREGIVER','FAMILY')")
    @Operation(summary = "Encontrar.", description = "Pesquisar por uma medicação dentro do sistema.")
    public ResponseEntity<MedicationResponseDTO> getByMedicationId(@PathVariable("medicationId") UUID medicationId) {
        return ResponseEntity.ok(service.getByIdDTO(medicationId));
    }

}
