package io.github.guiboava.bem_dosado.controller;

import io.github.guiboava.bem_dosado.controller.dto.PatientRequestDTO;
import io.github.guiboava.bem_dosado.controller.dto.PatientResponseDTO;
import io.github.guiboava.bem_dosado.controller.mappers.PatientMapper;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import io.github.guiboava.bem_dosado.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController implements GenericController {

    private final PatientService service;
    private final PatientMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createPatient(@RequestBody @Valid PatientRequestDTO patient) {

        var patientEntity = mapper.toEntity(patient);
        service.save(patientEntity);
        URI uri = generateHeaderLocation(patientEntity.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> searchPatient(@RequestParam(value = "name", required = false) String name,
                                                                  @RequestParam(value = "cpf", required = false) String cpf,
                                                                  @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
                                                                  @RequestParam(value = "gender", required = false) Gender gender,
                                                                  @RequestParam(value = "cep", required = false) String cep,
                                                                  @RequestParam(value = "dependency", required = false) Dependency dependency,
                                                                  @RequestParam(value = "healthPlan", required = false) String healthPlan,
                                                                  @RequestParam(value = "cardNumber", required = false) String cardNumber,
                                                                  @RequestParam(value = "allergies", required = false) String allergies,
                                                                  @RequestParam(value = "medications", required = false) String medications,
                                                                  @RequestParam(value = "note", required = false) String note
    ) {
        List<Patient> searchPatients = service.searchByExample(name, cpf, birthDate, gender, cep, dependency, healthPlan, cardNumber, allergies, medications, note);
        List<PatientResponseDTO> list = searchPatients.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getByPatientId(@PathVariable("id") String id) {
        UUID patientId = UUID.fromString(id);
        return service.getById(patientId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") String id) {
        UUID patientId = UUID.fromString(id);
        Optional<Patient> optionalPatient = service.getById(patientId);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(optionalPatient.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable("id") String id, @RequestBody @Valid PatientRequestDTO dto) {
        return service.getById(UUID.fromString(id))
                .map(patient -> {
                    mapper.updateEntityFromDto(dto, patient);

                    service.update(patient);

                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Paciente não encontrado!"));
    }

}
