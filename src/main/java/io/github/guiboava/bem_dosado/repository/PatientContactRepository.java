package io.github.guiboava.bem_dosado.repository;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.entity.model.PatientHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientContactRepository extends JpaRepository<PatientContact, UUID> {
    Optional<PatientContact> findByIdAndPatient(UUID contactId, Patient patient);

    List<PatientContact> findByPatient(Patient patient);

    boolean existsByPatientAndNameAndPhoneNumber(Patient patient,String name, String phoneNumber);

    boolean existsByPatientAndNameAndPhoneNumberAndIdNot(Patient patient,String name, String phoneNumber,UUID id);
}
