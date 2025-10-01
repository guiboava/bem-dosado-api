package io.github.guiboava.bem_dosado.repository;

import io.github.guiboava.bem_dosado.entity.model.Patient;
import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.TaskType;
import io.github.guiboava.bem_dosado.entity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUser(User user);

    Optional<Task> findByIdAndUser(UUID taskId, User user);

    //Casa não de nenhum erro em usar direto do service remover este codigo.
    Page<Task> findByUser(User user, Pageable pageable);

    boolean existsByPatientAndTaskTypeAndScheduledDate(Patient patient, TaskType taskType, LocalDateTime scheduledDate);

    boolean existsByPatientAndTaskTypeAndScheduledDateAndIdNot(Patient patient, TaskType taskType, LocalDateTime scheduledDate, UUID id);
}
