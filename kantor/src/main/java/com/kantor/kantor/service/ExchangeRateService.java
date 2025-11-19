package com.kantor.kantor.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.kantor.kantor.model.ExchangeRate;
import com.kantor.kantor.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {
    
private final ExchangeRateRepository exchangeRateRepository;

@Autowired
public ExchangeRateService(ExchangeRateRepository exchangeRateRepository){
    this.exchangeRateRepository = exchangeRateRepository;
}

public ExchangeRate updateExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate){
    Optional<ExchangeRate> existingRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);

    ExchangeRate exchangeRate;
    if (existingRate.isPresent()) {
        exchangeRate = existingRate.get();
        exchangeRate.setRate(rate);
    }else{
        exchangeRate = new ExchangeRate();
        exchangeRate.setFromCurrency(fromCurrency);
        exchangeRate.setToCurrency(toCurrency);
        exchangeRate.setRate(rate);
    }

    exchangeRate.setLastUpdated(LocalDateTime.now());
    return exchangeRateRepository.save(exchangeRate);
}

public Optional<ExchangeRate> getExchangeRate(String fromCurrency, String toCurrency){
    return exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
}

public List<ExchangeRate> getAllExchangeRates(){
    return exchangeRateRepository.findAll();
}

public BigDecimal convertAmount(String fromCurrency, String toCurrency, BigDecimal amount) {
        // Jeśli waluty są takie same, zwróć tę samą kwotę
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        
        // Znajdź kurs wymiany
        Optional<ExchangeRate> rateOpt = getExchangeRate(fromCurrency, toCurrency);
        
        if (rateOpt.isPresent()) {
            // Oblicz kwotę po przeliczeniu
            return amount.multiply(rateOpt.get().getRate());
        } else {
            // Sprawdź, czy istnieje odwrotny kurs
            Optional<ExchangeRate> reverseRateOpt = getExchangeRate(toCurrency, fromCurrency);
            
            if (reverseRateOpt.isPresent()) {
                // Oblicz używając odwrotności kursu
                BigDecimal reverseRate = BigDecimal.ONE.divide(reverseRateOpt.get().getRate(), 10, BigDecimal.ROUND_HALF_UP);
                return amount.multiply(reverseRate);
            } else {
                throw new RuntimeException("Nie znaleziono kursu wymiany dla pary walut: " + fromCurrency + "/" + toCurrency);
            }
        }
    }
}