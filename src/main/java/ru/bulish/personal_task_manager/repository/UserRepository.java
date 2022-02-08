package ru.bulish.personal_task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bulish.personal_task_manager.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
