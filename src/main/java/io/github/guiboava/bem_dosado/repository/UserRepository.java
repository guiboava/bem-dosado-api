package io.github.guiboava.bem_dosado.repository;

import io.github.guiboava.bem_dosado.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUserNameAndIdNot(String userName, UUID id);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByCpfAndIdNot(String cpf, UUID id);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
