package ru.bulish.personal_task_manager.service;

import org.springframework.stereotype.Service;
import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.entity.UserEntity;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    void saveOrUpdate(UserEntity user);
    void deleteById(Long id);

    UserEntity getUserByName(String name);

   String getAverageTime(Long id);
}
