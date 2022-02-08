package ru.bulish.personal_task_manager.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.entity.Level;
import ru.bulish.personal_task_manager.entity.UserEntity;
import ru.bulish.personal_task_manager.service.CardService;
import ru.bulish.personal_task_manager.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;



@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getOneCard(@PathVariable("id") Long id, Model model) {
       Card card = cardService.getCardById(id);
        if (card == null) {
            throw new NoSuchElementException ("There is no such product in Database");
        }
         model.addAttribute("card", card);
        return "cards/card";
    }



    @GetMapping("/list")
    public String showAllCards(Model model) {
        List<Card> cards = cardService.getAllCards();
        model.addAttribute("cards", cards);
        return "cards/cards_all";

    }
    @GetMapping("/completed")
    public String showAllCompletedCards(Model model) {
        List<Card> cards = cardService.getDisabledCards();
        model.addAttribute("cards", cards);
        return "cards/disabled_cards";

    }

    @GetMapping ("/new")
    public String showTheForm(Model model) {
        Card card = new Card();
    List<String> levels = new ArrayList<>();
    levels.add(Level.EASY.name());
    levels.add(Level.HARD.name());
    levels.add(Level.MEDIUM.name());

        model.addAttribute("levels", levels);
        List<UserEntity> users = userService.getAllUsers();

        model.addAttribute("card", card);
        model.addAttribute("users", users);
        return "cards/card_form";
    }

    @PostMapping
    public String addNewCard(@Valid @ModelAttribute("card") Card card, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "/cards/card_form";
        }
        if (card.getId() == null){
            Date dateOfCreation = new Date();
            card.setStart_time(dateOfCreation.getTime());
        }
       UserEntity user = userService.getUserByName(principal.getName());

        if (card.getUsers() == null){
            card.addUser(user);
        }
        cardService.saveOrUpdate(card);
        return "redirect:/user/main";
    }


    @GetMapping("/{id}/updateInfo")
    public String updateProduct(@PathVariable("id") Long id, Model model) {
        Card card = cardService.getCardById(id);
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("card", card);

        return "cards/card_form";
    }

    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        cardService.deleteById(id);
        return "redirect:/user/main";
    }
    @RequestMapping("/{id}/disable")
    public String makeDisable(@PathVariable("id") Long id) {
        Card card = cardService.getCardById(id);
         cardService.findTimeDifference(card);

        return "redirect:/user/main";
    }


}

