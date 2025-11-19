package com.kantor.kantor.testMockMvc;

import com.kantor.kantor.service.SystemLogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SystemLogControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemLogService systemLogService;

    @Test
    void userLogsShouldReturnOk() throws Exception {
        Mockito.when(systemLogService.getUserLogs(Mockito.any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/logs/user?username=user"))
                .andExpect(status().isOk());
    }

    @Test
    void allLogsShouldReturnOk() throws Exception {
        Mockito.when(systemLogService.getAllLogs()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/logs/all"))
                .andExpect(status().isOk());
    }

    @Test
    void userLogsNotFoundShouldReturnClientError() throws Exception {
        Mockito.when(systemLogService.getUserLogs(Mockito.any()))
                .thenThrow(new RuntimeException("Nie ma takiego użytkownika"));

        mockMvc.perform(get("/api/logs/user?username=brakTegoUsera"))
                .andExpect(status().is4xxClientError());
    }
}