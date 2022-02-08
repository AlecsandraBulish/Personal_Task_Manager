package ru.bulish.personal_task_manager.service;



import ru.bulish.personal_task_manager.entity.Card;

import java.util.List;

public interface CardAndUserService {
    public List<Card> getListOfCardsByUserId(Long id);
}
