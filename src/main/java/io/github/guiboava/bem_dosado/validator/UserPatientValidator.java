package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.exception.EntityInUseException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPatientValidator {

    private final UserRepository userRepository;

    public void validate(User user, Patient patient) {

        patientAlreadyAssigned(user, patient);

    }

    public void patientAlreadyAssigned(User user, Patient patient) {
        if (user.getPatients().contains(patient)) {
            throw new EntityInUseException(
                    String.format("O paciente: %s, já está vinculado ao usuário %s.", patient.getName(), user.getName()));
        }

    }

}
