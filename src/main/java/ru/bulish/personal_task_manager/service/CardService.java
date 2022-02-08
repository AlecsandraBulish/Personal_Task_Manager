package ru.bulish.personal_task_manager.service;


import org.springframework.stereotype.Service;
import ru.bulish.personal_task_manager.entity.Card;


import java.util.List;


@Service
public interface CardService {
    List<Card> getAllCards();
    Card getCardById(Long id);
    void saveOrUpdate(Card card);
    void deleteById(Long id);
    List<Card> getDisabledCards();
    Long findTimeDifference(Card card);

}

