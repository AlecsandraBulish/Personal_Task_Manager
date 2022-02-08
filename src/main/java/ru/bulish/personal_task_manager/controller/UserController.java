package ru.bulish.personal_task_manager.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.entity.UserEntity;
import ru.bulish.personal_task_manager.service.CardAndUserService;
import ru.bulish.personal_task_manager.service.RoleService;
import ru.bulish.personal_task_manager.service.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CardAndUserService cardAndUserService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public String getOneUser(@PathVariable("id") Long id, Model model) {
        UserEntity user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchElementException("There is no such user in Database");
        }
        logger.info("Getting the user by id: " + id + " with name-"+ user.getUsername());
        String result = userService.getAverageTime(id);
        model.addAttribute("user", user);
        model.addAttribute("average", result);
        return "users/user";
    }

    @GetMapping("/{id}/cards")
    public String getUserInfo(@PathVariable("id") Long id, Model model) {
        UserEntity user = userService.getUserById(id);
        List<Card> cards = cardAndUserService.getListOfCardsByUserId(id);
        if (user == null) {
            throw new NoSuchElementException("There is no such user in Database");
        }
        model.addAttribute("user", user);
        model.addAttribute("cards", cards);
        return "users/user_cards";
    }


    @GetMapping("/list")
    public String showAllUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users_all";

    }

    @GetMapping("/new")
    public String showTheForm(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return "users/user_form";
    }

    @PostMapping
    public String addNewUser(@Valid @ModelAttribute("user") UserEntity user, BindingResult result) {
        if (result.hasErrors()) {
            return "/users/user_form";
        }

        logger.info("Create or update user");
       userService.saveOrUpdate(user);
        return "redirect:/user/list";
    }


    @GetMapping("/{id}/updateInfo")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        UserEntity user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());

        return "users/user_form";
    }

    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        logger.info("User with id: " + id + " was deleted");
        return "redirect:/user/list";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("user", new UserEntity());

        return "users/registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") UserEntity user, BindingResult result) {
        if (result.hasErrors()) {
            return "/users/registration";
        }
      userService.saveOrUpdate(user);
      logger.info("User was created: " + user.getUsername());
        return "redirect:/login";


    }
    @GetMapping("/main")
    public String mainPage(Model model, Principal principal){
        UserEntity user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "main";
    }
    @GetMapping("/app")
    public String mainPageAnonim() {
        return "main";
    }
}



