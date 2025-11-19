package com.kantor.kantor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kantor.kantor.model.User;
import com.kantor.kantor.service.UserService;

@RestController 
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // REJESTRACJA nowego użytkownika
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // LOGOWANIE użytkownika
    @PostMapping("/login")
    public String login(@RequestBody User login) {
        User user = userService.findByUsername(login.getUsername())
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika"));
        boolean hasloOK = userService.isPasswordValid(user, login.getPassword());
        if (hasloOK) {
            return "Zalogowano jako: " + user.getUsername() + " rola: " + user.getRole();
        } else {
            throw new RuntimeException("Złe hasło");
        }
    }
}
