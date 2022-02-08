package ru.bulish.personal_task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bulish.personal_task_manager.entity.Card;
import ru.bulish.personal_task_manager.entity.RoleEntity;
import ru.bulish.personal_task_manager.entity.UserEntity;
import ru.bulish.personal_task_manager.repository.CardRepository;
import ru.bulish.personal_task_manager.repository.UserRepository;
import ru.bulish.personal_task_manager.service.CardAndUserService;
import ru.bulish.personal_task_manager.service.RoleService;
import ru.bulish.personal_task_manager.service.UserService;
import ru.bulish.personal_task_manager.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardAndUserService cardAndUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserServiceImpl userServ;

    @GetMapping("/{id}")
    public String getOneCard(@PathVariable("id") Long id, Model model) {
        UserEntity user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchElementException("There is no such user in Database");
        }
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
        return "redirect:/login";


    }
    @GetMapping("/main")
    public String mainPage(Model model, Principal principal){
        UserEntity user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "main";
    }
    @GetMapping("/app")
    public String mainPageAnonim(Model model, Principal principal) {
//        UserEntity user = userService.getUserByName(principal.getName());
//        model.addAttribute("user", user);
        return "main";
    }
}



