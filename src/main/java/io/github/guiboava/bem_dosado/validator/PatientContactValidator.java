package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.repository.PatientContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientContactValidator {

    private final PatientContactRepository repository;

    public void validate(PatientContact patientContact) {

        validateUniqueContact(patientContact);

    }

    public void validateUniqueContact(PatientContact contact) {
        if (contact.getId() == null) {
            boolean exists = repository.existsByPatientAndNameAndPhoneNumber(
                    contact.getPatient(),
                    contact.getName(),
                    contact.getPhoneNumber()
            );
            if (exists) {
                throw new DuplicateRegisterException("Já existe um contato com esse nome e telefone para este paciente");
            }
        } else {
            boolean exists = repository.existsByPatientAndNameAndPhoneNumberAndIdNot(
                    contact.getPatient(),
                    contact.getName(),
                    contact.getPhoneNumber(),
                    contact.getId()
            );
            if (exists) {
                throw new DuplicateRegisterException("Já existe um contato com esse nome e telefone para este paciente");
            }
        }
    }


}
