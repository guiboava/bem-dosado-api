package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.ClientRequestDTO;
import io.github.guiboava.bem_dosado.controller.mappers.ClientMapper;
import io.github.guiboava.bem_dosado.entity.model.Client;
import io.github.guiboava.bem_dosado.repository.ClientRepository;
import io.github.guiboava.bem_dosado.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final ClientValidator validator;
    private final PasswordEncoder encoder;

    public Client save(ClientRequestDTO dto) {

        Client client = mapper.toEntity(dto);
        validator.validate(client);
        var encryptedPassword = encoder.encode(client.getClientSecret());
        client.setClientSecret(encryptedPassword);
        return repository.save(client);

    }

    public Client getByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }

}
