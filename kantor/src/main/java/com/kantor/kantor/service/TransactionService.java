package com.kantor.kantor.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kantor.kantor.model.Transaction;
import com.kantor.kantor.model.User;
import com.kantor.kantor.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ExchangeRateService exchangeRateService;
    private final SystemLogService systemLogService;

    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            ExchangeRateService exchangeRateService,
            SystemLogService systemLogService) {
        this.transactionRepository = transactionRepository;
        this.exchangeRateService = exchangeRateService;
        this.systemLogService = systemLogService;
    }

    public Transaction createTransaction(User user, String fromCurrency, String toCurrency, BigDecimal fromAmount) {
        // Pobierz kurs wymiany
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate(fromCurrency, toCurrency)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono kursu wymiany"))
                .getRate();
        
        // Oblicz kwotę docelową
        BigDecimal toAmount = fromAmount.multiply(exchangeRate);
        
        // Utwórz transakcję
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setFromCurrency(fromCurrency);
        transaction.setToCurrency(toCurrency);
        transaction.setFromAmount(fromAmount);
        transaction.setToAmount(toAmount);
        transaction.setExchangeRate(exchangeRate);
        transaction.setTimestamp(LocalDateTime.now());
        
        // Zapisz transakcję
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // Dodaj log systemowy
        systemLogService.logAction(
            "TRANSACTION", 
            "Użytkownik " + user.getUsername() + " wymienił " + fromAmount + " " + 
            fromCurrency + " na " + toAmount + " " + toCurrency, 
            user
        );
        
        return savedTransaction;
    }
    
    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUser(user);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}