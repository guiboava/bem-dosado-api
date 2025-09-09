package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.MedicationResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.MedicationMapper;
import io.github.guiboava.bem_dosado.entity.model.Medication;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.repository.MedicationRepository;
import io.github.guiboava.bem_dosado.validator.MedicationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository repository;
    private final MedicationValidator validator;
    private final MedicationMapper mapper;

    public Optional<Medication> getById(UUID medicationId) {
        return repository.findById(medicationId);
    }

    public Medication save(Medication medication) {

        validator.validate(medication);
        return repository.save(medication);

    }

    public void update(Medication medication) {

        if (medication.getId() == null) {
            throw new OperationNotPermittedException("Para atualizar o cadastro de um medicamento é nescessário que o dado esteja salvo na base.");
        }

        validator.validate(medication);
        repository.save(medication);

    }

    public void delete(Medication medication) {
        repository.delete(medication);
    }

    public List<MedicationResponseDTO> getAll() {

        List<Medication> medications = repository.findAll();

        return medications.stream()
                .map(mapper::toDTO)
                .toList();
    }

}
