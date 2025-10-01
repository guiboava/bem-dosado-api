package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyResponseDTO;
import io.github.guiboava.bem_dosado.service.PublicEmergencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/public-emergency")
@RequiredArgsConstructor
public class PublicEmergencyController implements GenericController {

    private final PublicEmergencyService service;

    @PostMapping
    public ResponseEntity<Void> createPublicEmergency(@RequestBody @Valid PublicEmergencyRequestDTO dto) {

        URI uri = generateHeaderLocation(service.save(dto));

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{publicEmergencyId}")
    public ResponseEntity<Void> updatePublicEmergency(
            @PathVariable("publicEmergencyId") UUID publicEmergencyId,
            @RequestBody @Valid PublicEmergencyRequestDTO dto) {

        service.update(dto, publicEmergencyId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{publicEmergencyId}")
    public ResponseEntity<Void> deletePublicEmergency(@PathVariable("publicEmergencyId") UUID publicEmergencyId) {

        service.delete(publicEmergencyId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PublicEmergencyResponseDTO>> getAllPublicEmergencies() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{publicEmergencyId}")
    public ResponseEntity<PublicEmergencyResponseDTO> getByPublicEmergencyId(@PathVariable("publicEmergencyId") UUID publicEmergencyId) {

        return ResponseEntity.ok(service.getByIdDTO(publicEmergencyId));
    }

}
