package ru.bulish.personal_task_manager.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bulish.personal_task_manager.entity.RoleEntity;
import ru.bulish.personal_task_manager.repository.RoleRepository;
import ru.bulish.personal_task_manager.service.RoleService;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

}
