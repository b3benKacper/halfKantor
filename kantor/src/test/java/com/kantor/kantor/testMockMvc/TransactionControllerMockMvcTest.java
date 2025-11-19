package com.kantor.kantor.testMockMvc;

import com.kantor.kantor.model.Transaction;
import com.kantor.kantor.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void exchangeShouldReturnOk() throws Exception {
        Mockito.when(transactionService.createTransaction(
                Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(BigDecimal.class)
        )).thenReturn(new Transaction());

        mockMvc.perform(post("/api/transactions/exchange?username=user&from=PLN&to=USD&amount=10"))
                .andExpect(status().isOk());
    }

    @Test
    void userHistoryShouldReturnOk() throws Exception {
        Mockito.when(transactionService.getUserTransactions(Mockito.any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transactions/user?username=user"))
                .andExpect(status().isOk());
    }

    @Test
    void allTransactionsShouldReturnOk() throws Exception {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transactions/all"))
                .andExpect(status().isOk());
    }
}