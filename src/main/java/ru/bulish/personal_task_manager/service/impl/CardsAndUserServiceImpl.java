package ru.bulish.personal_task_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.entity.UserEntity;
import ru.bulish.personal_task_manager.repository.CardRepository;
import ru.bulish.personal_task_manager.repository.UserRepository;
import ru.bulish.personal_task_manager.service.CardAndUserService;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CardsAndUserServiceImpl implements CardAndUserService {
    private final CardRepository cardRepository;
    private final UserRepository userRepo;



    @Override
    public List<Card> getListOfCardsByUserId(Long id) {
        UserEntity user = userRepo.getById(id);
        List<Card> cards = user.getCards();
        return cards;
    }
}
