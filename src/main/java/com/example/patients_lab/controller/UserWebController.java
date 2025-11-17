package com.example.patients_lab.controller;

import com.example.patients_lab.model.Role;
import com.example.patients_lab.model.User;
import com.example.patients_lab.repository.UserRepository;
import com.example.patients_lab.service.UserService;
import com.example.patients_lab.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UserWebController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private final UserRepository userRepository;

    public UserWebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String userPage(Model model, @RequestParam(required = false) String msg) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("message", msg);
        return "users";
    }

    @PostMapping("/update")
    public String updateRole(@RequestParam Long id,
                             @RequestParam Role role,
                             RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
            redirectAttributes.addAttribute("msg", "Роль пользователя обновлена");
        } else {
            redirectAttributes.addAttribute("msg", "Пользователь не найден");
        }
        return "redirect:/users";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "Пользователь удален";
        } else {
            return "Пользователь не найден";
        }
    }

}
