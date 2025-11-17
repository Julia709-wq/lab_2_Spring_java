package com.example.patients_lab.config;

import com.example.patients_lab.model.Role;
import com.example.patients_lab.model.User;
import com.example.patients_lab.repository.PatientRepository;
import com.example.patients_lab.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AppInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        createUserIfNotExists("superadmin@example.com", "superadmin00", Role.SUPER_ADMIN);
        createUserIfNotExists("admin@example.com", "admin11", Role.ADMIN);
        createUserIfNotExists("user@example.com", "user22", Role.USER);
    }

    private void createUserIfNotExists(String email, String rawPassword, Role role) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Создан пользователь " + role + ": " + email);

        }
    }
}

