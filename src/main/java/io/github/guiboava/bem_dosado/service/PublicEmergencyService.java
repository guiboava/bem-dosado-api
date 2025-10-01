package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PublicEmergencyMapper;
import io.github.guiboava.bem_dosado.entity.model.PublicEmergency;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.PublicEmergencyRepository;
import io.github.guiboava.bem_dosado.validator.PublicEmergencyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicEmergencyService {

    private final PublicEmergencyRepository repository;
    private final PublicEmergencyValidator validator;
    private final PublicEmergencyMapper mapper;


    public UUID save(PublicEmergencyRequestDTO dto) {

        PublicEmergency publicEmergency = mapper.toEntity(dto);

        validator.validate(publicEmergency);
        return repository.save(publicEmergency).getId();

    }

    public void update(PublicEmergencyRequestDTO dto, UUID publicEmergencyId) {

        PublicEmergency publicEmergency = getById(publicEmergencyId);

        mapper.updateEntityFromDto(dto, publicEmergency);
        validator.validate(publicEmergency);
        repository.save(publicEmergency);

    }

    public void delete(UUID publicEmergencyId) {


        PublicEmergency publicEmergency = getById(publicEmergencyId);

        repository.delete(publicEmergency);

    }

    public List<PublicEmergencyResponseDTO> getAll() {

        List<PublicEmergency> emergencies = repository.findAll();
        return emergencies.stream()
                .map(mapper::toDTO)
                .toList();

    }

    public PublicEmergencyResponseDTO getByIdDTO(UUID publicEmergencyId) {
        PublicEmergency publicEmergency = getById(publicEmergencyId);
        return mapper.toDTO(publicEmergency);
    }

    public PublicEmergency getById(UUID publicEmergencyId) {
        return repository.findById(publicEmergencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado dados de contato de serviço publico para o id " + publicEmergencyId));
    }
}
