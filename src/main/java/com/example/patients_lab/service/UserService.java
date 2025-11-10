package com.example.patients_lab.service;

import com.example.patients_lab.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    boolean register(User user);
    boolean EmailExists(String email);

    User findByEmail(String email);

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void deleteById(Long id);
}
