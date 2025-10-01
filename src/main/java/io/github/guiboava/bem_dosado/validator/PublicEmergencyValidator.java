package io.github.guiboava.bem_dosado.validator;

import io.github.guiboava.bem_dosado.entity.model.PublicEmergency;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.repository.PublicEmergencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicEmergencyValidator {

    private final PublicEmergencyRepository repository;

    public void validate(PublicEmergency publicEmergency) {
        validateUniqueServiceName(publicEmergency);
        validateUniquePhoneNumber(publicEmergency);
    }

    private void validateUniqueServiceName(PublicEmergency publicEmergency) {
        if (publicEmergency.getId() == null) {
            boolean exists = repository.existsByServiceName(publicEmergency.getServiceName());
            if (exists) {
                throw new DuplicateRegisterException("Já existe um contato público com o nome: " + publicEmergency.getServiceName());
            }
        } else {
            boolean exists = repository.existsByServiceNameAndIdNot(
                    publicEmergency.getServiceName(),
                    publicEmergency.getId()
            );
            if (exists) {
                throw new DuplicateRegisterException("Já existe um contato público com o nome: " + publicEmergency.getServiceName());
            }
        }
    }

    private void validateUniquePhoneNumber(PublicEmergency publicEmergency) {
        if (publicEmergency.getId() == null) {
            boolean exists = repository.existsByPhoneNumber(publicEmergency.getPhoneNumber());
            if (exists) {
                throw new DuplicateRegisterException("Já existe um contato público com o telefone: " + publicEmergency.getPhoneNumber());
            }
        } else {
            boolean exists = repository.existsByPhoneNumberAndIdNot(
                    publicEmergency.getPhoneNumber(),
                    publicEmergency.getId()
            );
            if (exists) {
                throw new DuplicateRegisterException("Já existe um contato público com o telefone: " + publicEmergency.getPhoneNumber());
            }
        }
    }
}
