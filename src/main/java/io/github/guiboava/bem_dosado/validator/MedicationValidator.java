package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Medication;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.EntityInUseException;
import io.github.guiboava.bem_dosado.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicationValidator {

    private final MedicationRepository repository;

    public void validate(Medication medication) {

        duplicateRegister(medication);

    }

    private void duplicateRegister(Medication medication) {
        if (medication.getId() == null) {
            if (repository.existsByName(medication.getName())) {
                throw new DuplicateRegisterException("Já existe um medicamento com este nome: " + medication.getName()
                );
            }
        } else {
            if (repository.existsByNameAndIdNot(medication.getName(), medication.getId())) {
                throw new DuplicateRegisterException("Já existe um medicamento com este nome: " + medication.getName()
                );
            }
        }
    }

    public void validateNotLinkedToTasks(Medication medication) {
        if (!medication.getTasks().isEmpty()) {
            throw new EntityInUseException(
                    String.format("Não é possível deletar o medicamento: %s, ele está vinculado a tarefas.", medication.getName()));
        }
    }
}
