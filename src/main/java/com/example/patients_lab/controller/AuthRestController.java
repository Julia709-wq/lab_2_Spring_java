package com.example.patients_lab.controller;

import com.example.patients_lab.model.User;
import com.example.patients_lab.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(summary = "Вход в систему", description = "Аутентифицирует пользователя и возвращает JWT-токен")
    @ApiResponse(responseCode = "200", description = "Аутентификация успешна")
    @ApiResponse(responseCode = "401", description = "Неверные учётные данные")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
