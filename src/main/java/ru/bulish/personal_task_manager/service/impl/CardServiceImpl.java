package ru.bulish.personal_task_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.repository.CardRepository;
import ru.bulish.personal_task_manager.service.CardService;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    @Transactional
    public List<Card> getAllCards() {
       List<Card> cards = cardRepository.findAll();
       return cards;
    }

    @Override
    @Transactional
    public Card getCardById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        return card;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Card card) {
        cardRepository.saveAndFlush(card);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
    cardRepository.deleteById(id);
    }

    @Override
    public List<Card> getDisabledCards() {
        List<Card> cards = cardRepository.findDisabledCards();
        return cards;
    }

    @Override
    public Long findTimeDifference(Card card) {
        Date dateOfEnd = new Date();
        card.setEnd_time(dateOfEnd.getTime());
        long start = card.getStart_time();
        long difference = dateOfEnd.getTime() - start;
        String str = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(difference),
                TimeUnit.MILLISECONDS.toMinutes(difference) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(difference) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
        card.setSummary_time(str);
        card.setEnabled(false);
        cardRepository.saveAndFlush(card);
        return difference;
    }



}
