package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.EntityInUseException;
import io.github.guiboava.bem_dosado.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientValidator {

    private final PatientRepository repository;

    public void validate(Patient patient) {

        duplicateRegister(patient);

    }

    private void duplicateRegister(Patient patient) {
        if (patient.getId() == null) {
            if (repository.existsByCpf(patient.getCpf())) {
                throw new DuplicateRegisterException("Já existe um paciente com este CPF.");
            }
        } else {
            if (repository.existsByCpfAndIdNot(patient.getCpf(), patient.getId())) {
                throw new DuplicateRegisterException("Já existe um paciente com este CPF.");
            }
        }
    }

    public void validateNotLinkedToUsers(Patient patient) {
        if (!patient.getUsers().isEmpty()) {
            throw new EntityInUseException(
                    String.format("Não é possível deletar o paciente: %s, ele está vinculado a outros usuários.", patient.getName()));

        }
    }
}
