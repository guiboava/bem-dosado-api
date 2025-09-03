package io.github.guiboava.bem_dosado.repository;

import aj.org.objectweb.asm.commons.Remapper;
import io.github.guiboava.bem_dosado.controller.dto.PatientHealthResponseDTO;
import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientHealthRepository extends JpaRepository<PatientHealth, UUID> {

    List<PatientHealth> findByPatient(Patient patient);

    Optional<PatientHealth> findByIdAndPatient(UUID id, Patient patient);
}
