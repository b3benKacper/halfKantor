package com.kantor.kantor.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String fromCurrency; // PLN / USD

@Column(nullable = false)
private String toCurrency; // USD / PLN

@Column(nullable = false, precision = 10, scale = 4)
private BigDecimal rate;

@Column(nullable = false)
private LocalDateTime lastUpdated;


// Gettery i settery

public Long getId(){
    return id;
}
public void setId(Long id){
    this.id = id;
}

public String getFromCurrency(){
    return fromCurrency;
}
public void setFromCurrency(String fromCurrency){
    this.fromCurrency = fromCurrency;
}

public String getToCurrency(){
    return toCurrency;
}
public void setToCurrency(String toCurrency){
    this.toCurrency = toCurrency;
}

public BigDecimal getRate(){
    return rate;
}
public void setRate(BigDecimal rate){
    this.rate = rate;
}

public LocalDateTime getLastUpdated(){
    return lastUpdated;
}
public void setLastUpdated(LocalDateTime lastUpdated){
    this.lastUpdated = lastUpdated;
}

}
