package com.kantor.kantor.testMockMvc;

import com.kantor.kantor.model.ExchangeRate;
import com.kantor.kantor.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = com.kantor.kantor.controller.ExchangeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExchangeControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    void getRateShouldReturnOk() throws Exception {
        ExchangeRate rate = new ExchangeRate();
        rate.setRate(BigDecimal.valueOf(4.0));
        Mockito.when(exchangeRateService.getExchangeRate("PLN", "USD"))
               .thenReturn(Optional.of(rate));

        mockMvc.perform(get("/api/exchange/rate?from=PLN&to=USD"))
                .andExpect(status().isOk());
    }

    @Test
    void convertShouldReturnOk() throws Exception {
        Mockito.when(exchangeRateService.convertAmount("PLN", "USD", BigDecimal.valueOf(100)))
               .thenReturn(BigDecimal.valueOf(25.0));

        mockMvc.perform(get("/api/exchange/convert?from=PLN&to=USD&amount=100"))
                .andExpect(status().isOk());
    }

    @Test
    void setRateShouldReturnOk() throws Exception {
        ExchangeRate newRate = new ExchangeRate();
        newRate.setFromCurrency("PLN");
        newRate.setToCurrency("USD");
        newRate.setRate(BigDecimal.valueOf(4.0));
        Mockito.when(exchangeRateService.updateExchangeRate("PLN", "USD", BigDecimal.valueOf(4.0)))
               .thenReturn(newRate);

        mockMvc.perform(post("/api/exchange/rate?from=PLN&to=USD&rate=4.0"))
                .andExpect(status().isOk());
    }
}
