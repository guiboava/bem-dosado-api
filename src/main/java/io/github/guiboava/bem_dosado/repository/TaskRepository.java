package io.github.guiboava.bem_dosado.repository;

import io.github.guiboava.bem_dosado.entity.model.Task;
import io.github.guiboava.bem_dosado.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUser(User user);

    Optional<Task> findByIdAndUser(UUID taskId, User userId);
}
