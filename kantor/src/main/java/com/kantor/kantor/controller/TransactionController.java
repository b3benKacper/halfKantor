package com.kantor.kantor.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kantor.kantor.model.Transaction;
import com.kantor.kantor.model.User;
import com.kantor.kantor.service.TransactionService;
import com.kantor.kantor.service.UserService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    // WYKONAJ transakcję (wymiana walut)
    @PostMapping("/exchange")
    public Transaction exchange(
            @RequestParam String username,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount) {
        // Szukamy użytkownika po nazwie
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika"));
        return transactionService.createTransaction(user, from, to, amount);
    }

    // Historia tego użytkownika
    @GetMapping("/user")
    public List<Transaction> userHistory(@RequestParam String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika"));
        return transactionService.getUserTransactions(user);
    }

    // Wszystkie transakcje (widoczne dla admina)
    @GetMapping("/all")
    public List<Transaction> allTransactions() {
        return transactionService.getAllTransactions();
    }
}
