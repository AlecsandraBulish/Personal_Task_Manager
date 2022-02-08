package ru.bulish.personal_task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.bulish.personal_task_manager.entity.Card;

import java.util.List;


public interface CardRepository extends JpaRepository<Card,Long> {

    @Query("SELECT u FROM Card u where  u.enabled=false")
    public List<Card> findDisabledCards();

}
