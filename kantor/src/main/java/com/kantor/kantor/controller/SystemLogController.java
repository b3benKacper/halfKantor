package com.kantor.kantor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kantor.kantor.model.SystemLog;
import com.kantor.kantor.model.User;
import com.kantor.kantor.service.SystemLogService;
import com.kantor.kantor.service.UserService;

@RestController
@RequestMapping("/api/logs")
public class SystemLogController {

    private final SystemLogService systemLogService;
    private final UserService userService;

    @Autowired
    public SystemLogController(SystemLogService systemLogService, UserService userService) {
        this.systemLogService = systemLogService;
        this.userService = userService;
    }

    // Pokaż logi jednego użytkownika (do historii użytkownika)
    @GetMapping("/user")
    public List<SystemLog> userLogs(@RequestParam String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika"));
        return systemLogService.getUserLogs(user);
    }

    // Pokaż wszystkie logi (admin)
    @GetMapping("/all")
    public List<SystemLog> allLogs() {
        return systemLogService.getAllLogs();
    }
}
