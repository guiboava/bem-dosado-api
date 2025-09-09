package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PublicEmergencyResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PublicEmergencyMapper;
import io.github.guiboava.bem_dosado.entity.model.PublicEmergency;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.PublicEmergencyRepository;
import io.github.guiboava.bem_dosado.validator.PublicEmergencyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicEmergencyService {

    private final PublicEmergencyRepository repository;
    private final PublicEmergencyValidator validator;
    private final PublicEmergencyMapper mapper;


    public PublicEmergency save(PublicEmergency publicEmergency) {

        validator.validate(publicEmergency);
        return repository.save(publicEmergency);

    }

    public Optional<PublicEmergency> getById(UUID publicEmergencyId) {
        return repository.findById(publicEmergencyId);
    }

    public void update(PublicEmergency publicEmergency) {

        if (publicEmergency.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de um contato de emergencia publica é nescessário que o dado esteja salvo na base.");
        }

        validator.validate(publicEmergency);
        repository.save(publicEmergency);

    }

    public void delete(PublicEmergency publicEmergency) {

        repository.delete(publicEmergency);

    }

    public List<PublicEmergencyResponseDTO> getAll() {

        List<PublicEmergency> taskTypesList = repository.findAll();

        return taskTypesList.stream()
                .map(mapper::toDTO)
                .toList();

    }
}
