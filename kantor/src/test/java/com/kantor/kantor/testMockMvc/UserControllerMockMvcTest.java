package com.kantor.kantor.testMockMvc;

import com.kantor.kantor.model.User;
import com.kantor.kantor.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void registerUserShouldReturnOk() throws Exception {
        User user = new User();
        user.setUsername("unit1");
        user.setPassword("pass1");
        Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"unit1\", \"password\":\"pass1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void registerUserShouldReturnClientError() throws Exception {
        Mockito.when(userService.registerUser(Mockito.any(User.class)))
                .thenThrow(new RuntimeException("Użytkownik o tej nazwie juz istnieje"));

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"unit1\", \"password\":\"pass1\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void loginUserShouldReturnOk() throws Exception {
        User user = new User();
        user.setUsername("unit1");
        user.setPassword("pass1");
        user.setRole("USER");
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(java.util.Optional.of(user));
        Mockito.when(userService.isPasswordValid(Mockito.any(User.class), Mockito.anyString())).thenReturn(true);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"unit1\", \"password\":\"pass1\"}"))
                .andExpect(status().isOk());
    }
}