package ru.bulish.personal_task_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.entity.RoleEntity;
import ru.bulish.personal_task_manager.entity.UserEntity;
import ru.bulish.personal_task_manager.repository.RoleRepository;
import ru.bulish.personal_task_manager.repository.UserRepository;
import ru.bulish.personal_task_manager.service.RoleService;
import ru.bulish.personal_task_manager.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;



    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    @Override
    public UserEntity getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return user;
    }



    @Override
    public void saveOrUpdate(UserEntity user) {
        UserEntity newUser = new UserEntity(user);
        newUser.setPassword(encoder.encode(user.getPassword()));
        if (newUser.getId()==null){
            newUser.setId(user.getId());
        }
        if (newUser.getRoles().isEmpty() || newUser.getRoles()== null){
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName("ROLE_USER");
            newUser.setRoles(new ArrayList<>(Collections.singleton(roleEntity)));
        }
        userRepository.saveAndFlush(newUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity getUserByName(String name) {
       UserEntity user = userRepository.findByUsername(name).orElse(null);
       return user;
    }


    @Override
    @Transactional
    public String getAverageTime(Long id) {
      UserEntity userEntity =getUserById(id);
      List<Card>cards = userEntity.getCards();
      Long count=0L;
      Long av = 0L;
        for (Card c:cards) {
            if (c.getEnabled()==false){
                count++;
               Long temp = c.getEnd_time()-c.getStart_time();
                av += temp;
            }
        }
       Long average =  av/count;

        String str = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(average),
                TimeUnit.MILLISECONDS.toMinutes(average) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(average)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(average) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(average)));
    //    userEntity.setAverage(average);
        return str;
    }
}
