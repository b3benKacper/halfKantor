package com.kantor.kantor.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kantor.kantor.model.ExchangeRate;
import com.kantor.kantor.service.ExchangeRateService;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    // Pobierz kurs dla podanej pary walut
    @GetMapping("/rate")
    public BigDecimal getRate(
            @RequestParam String from,
            @RequestParam String to) {
        Optional<ExchangeRate> rate = exchangeRateService.getExchangeRate(from, to);
        return rate.map(ExchangeRate::getRate)
                .orElseThrow(() -> new RuntimeException("Brak kursu"));
    }

    // Przelicz kwotę
    @GetMapping("/convert")
    public BigDecimal convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount) {
        return exchangeRateService.convertAmount(from, to, amount);
    }

    // Dodaj/zmień kurs wymiany 
    @PostMapping("/rate")
    public ExchangeRate setRate(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal rate) {
        return exchangeRateService.updateExchangeRate(from, to, rate);
    }

    // Pobierz wszystkie kursy walut
    @GetMapping("/all")
    public List<ExchangeRate> allRates() {
        return exchangeRateService.getAllExchangeRates();
    }
}
