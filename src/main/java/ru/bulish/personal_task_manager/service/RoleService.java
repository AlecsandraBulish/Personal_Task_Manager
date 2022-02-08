package ru.bulish.personal_task_manager.service;



import ru.bulish.personal_task_manager.entity.RoleEntity;


import java.util.List;

public interface RoleService {

    RoleEntity findByName(String name);

    List<RoleEntity> findAll();
}
