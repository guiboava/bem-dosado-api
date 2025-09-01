package io.github.guiboava.bem_dosado.repository;

import io.github.guiboava.bem_dosado.entity.model.PatientContact;
import io.github.guiboava.bem_dosado.entity.model.PublicEmergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PublicEmergencyRepository extends JpaRepository<PublicEmergency, UUID> {
}
