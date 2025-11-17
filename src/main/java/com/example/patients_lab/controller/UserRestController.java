package com.example.patients_lab.controller;

import com.example.patients_lab.model.Role;
import com.example.patients_lab.model.User;
import com.example.patients_lab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех зарегистрированных пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей успешно возвращен")
    @GetMapping
    public List<User> getAllUsers() {return userService.findAll();}


    @Operation(summary = "Изменить роль пользователя", description = "Обновляет роль указанного пользователя")
    @ApiResponse(responseCode = "200", description = "Роль пользователя успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestParam Role role) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        user.setRole(role);
        userService.save(user);
        return ResponseEntity.ok().body("Роль пользователя обновлена");
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя с указанным идентификатором")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно удалён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        userService.deleteById(id);
        return ResponseEntity.ok().body("Пользователь удалён");
    }
}
