package io.github.guiboava.bem_dosado.repository;

import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientContactRepository extends JpaRepository<PatientContact, UUID> {
}
