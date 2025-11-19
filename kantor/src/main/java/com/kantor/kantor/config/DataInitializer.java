package com.kantor.kantor.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kantor.kantor.model.User;
import com.kantor.kantor.service.ExchangeRateService;
import com.kantor.kantor.service.SystemLogService;
import com.kantor.kantor.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final ExchangeRateService exchangeRateService;
    private final SystemLogService systemLogService;

    @Autowired
    public DataInitializer(
            UserService userService,
            ExchangeRateService exchangeRateService,
            SystemLogService systemLogService) {
        this.userService = userService;
        this.exchangeRateService = exchangeRateService;
        this.systemLogService = systemLogService;
    }

    @Override
    public void run(String... args) {
        // Sprawdź czy istnieje użytkownik admin
        if (userService.findByUsername("admin").isEmpty()) {
            // Utwórz admina
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123"); 
            admin.setRole("ADMIN");
            User savedAdmin = userService.registerUser(admin);
            
            systemLogService.logAction("SYSTEM", "Utworzono konto administratora", savedAdmin);
        }
        
        // Sprawdź czy istnieje użytkownik testowy
        if (userService.findByUsername("user").isEmpty()) {
            // Utwórz użytkownika testowego
            User testUser = new User();
            testUser.setUsername("user");
            testUser.setPassword("user123");
            testUser.setRole("USER");
            User savedUser = userService.registerUser(testUser);
            
            systemLogService.logAction("SYSTEM", "Utworzono konto użytkownika testowego", savedUser);
        }
        
        // Dodaj początkowe kursy walut
        exchangeRateService.updateExchangeRate("USD", "PLN", new BigDecimal("4.00"));
        exchangeRateService.updateExchangeRate("PLN", "USD", new BigDecimal("0.25"));
        
        systemLogService.logAction("SYSTEM", "Zainicjalizowano kursy walut", null);
    }
}
