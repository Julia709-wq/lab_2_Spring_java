package com.example.patients_lab.controller;

import com.example.patients_lab.model.User;
import com.example.patients_lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthWebController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        boolean success = userService.register(user);
        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {return "login";}
}
