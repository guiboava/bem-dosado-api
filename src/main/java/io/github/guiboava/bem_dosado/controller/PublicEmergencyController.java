package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PublicEmergencyMapper;
import io.github.guiboava.bem_dosado.entity.model.PublicEmergency;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
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
    private final PublicEmergencyMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createPublicEmergency(@RequestBody @Valid PublicEmergencyRequestDTO dto) {

        PublicEmergency publicEmergency = mapper.toEntity(dto);

        service.save(publicEmergency);

        URI uri = generateHeaderLocation(publicEmergency.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{publicEmergencyId}")
    public ResponseEntity<Void> updatePublicEmergency(
            @PathVariable("publicEmergencyId") UUID publicEmergencyId,
            @RequestBody @Valid PublicEmergencyRequestDTO dto) {

        PublicEmergency publicEmergency = service.getById(publicEmergencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dados de contato de serviço publico para o id " + publicEmergencyId));

        mapper.updateEntityFromDto(dto, publicEmergency);

        service.update(publicEmergency);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{publicEmergencyId}")
    public ResponseEntity<Void> deletePublicEmergency(@PathVariable("publicEmergencyId") UUID publicEmergencyId) {

        PublicEmergency publicEmergency = service
                .getById(publicEmergencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dados de contato de serviço publico para o id " + publicEmergencyId));

        service.delete(publicEmergency);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PublicEmergencyResponseDTO>> getAllPublicEmergencies() {

        List<PublicEmergencyResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{publicEmergencyId}")
    public ResponseEntity<PublicEmergencyResponseDTO> getByPublicEmergencyId(@PathVariable("publicEmergencyId") UUID publicEmergencyId) {

        return service.getById(publicEmergencyId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dados de contato de serviço publico para o id " + publicEmergencyId));
    }

}
