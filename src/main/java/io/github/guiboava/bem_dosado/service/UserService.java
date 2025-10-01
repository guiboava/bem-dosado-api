package io.github.guiboava.bem_dosado.service;

import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.UserResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientMapper;
import io.github.guiboava.bem_dosado.controller.mappers.UserMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.User;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.entity.model.enums.UserType;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import io.github.guiboava.bem_dosado.repository.UserRepository;
import io.github.guiboava.bem_dosado.validator.UserPatientValidator;
import io.github.guiboava.bem_dosado.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PatientService patientService;
    private final UserValidator userValidator;
    private final UserPatientValidator userPatientValidator;
    private final UserMapper userMapper;
    private final PatientMapper patientMapper;

    public UUID save(UserRequestDTO dto) {

        User user = userMapper.toEntity(dto);
        userValidator.validate(user);
        return userRepository.save(user).getId();
    }

    public void update(UUID userId, UserRequestDTO dto) {

        User user = getEntityById(userId);

        userMapper.updateEntityFromDto(dto, user);
        userValidator.validate(user);
        userRepository.save(user);
    }

    public void delete(UUID userId) {

        User user = getEntityById(userId);
        userValidator.validateNotLinkedToPatients(user);
        userRepository.delete(user);
    }

    public UserResponseDTO getById(UUID userId) {

        return userRepository.findById(userId)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum dado de usuário para o paciente."));
    }

    public User getEntityById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado para o id " + userId));

    }

    public List<UserResponseDTO> searchByExample(String name,
                                                 String userName,
                                                 String email,
                                                 String cpf,
                                                 UserType userType,
                                                 Gender gender,
                                                 String phoneNumber,
                                                 LocalDate birthDate) {

        var user = new User();
        user.setName(name);
        user.setUserName(userName);
        user.setEmail(email);
        user.setCpf(cpf);
        user.setUserType(userType);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> userExample = Example.of(user, matcher);
        return userRepository.findAll(userExample).stream()
                .map(userMapper::toDTO)
                .toList();

    }

    @Transactional
    public void addPatientToUser(UUID userId, UUID patientId) {
        User user = getEntityById(userId);
        Patient patient = patientService.getEntityById(patientId);

        userPatientValidator.validate(user, patient);

        user.getPatients().add(patient);
        userRepository.save(user);
    }

    @Transactional
    public void removePatientFromUser(UUID userId, UUID patientId) {
        User user = getEntityById(userId);
        Patient patient = patientService.getEntityById(patientId);

        user.getPatients().remove(patient);
        userRepository.save(user);
    }

    public List<PatientResponseDTO> getUserPatients(UUID userId) {
        User user = getEntityById(userId);
        return new ArrayList<>(user.getPatients())
                .stream()
                .map(patientMapper::toDTO)
                .toList();
    }

}
