package ru.bulish.personal_task_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.bulish.personal_task_manager.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByNameIgnoreCase(String name);

    Optional<RoleEntity> findByName(String role_user);
}
