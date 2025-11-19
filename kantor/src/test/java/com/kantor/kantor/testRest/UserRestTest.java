package com.kantor.kantor.testRest;

import com.kantor.kantor.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class UserRestTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API = "http://localhost:8089/api/users";

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("testU123");
        user.setPassword("passU123");
        ResponseEntity<User> response = restTemplate.postForEntity(API + "/register", user, User.class);
        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testLoginUser() {
        User user = new User();
        user.setUsername("testU123");
        user.setPassword("passU123");
        ResponseEntity<String> response = restTemplate.postForEntity(API + "/login", user, String.class);
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null && response.getBody().contains("Zalogowano jako");
    }

    @Test
    void testRegisterExistingUser() {
        User user = new User();
        user.setUsername("passU123");
        user.setPassword("passU123");
        ResponseEntity<String> response = restTemplate.postForEntity(API + "/register", user, String.class);
        assert response.getStatusCode().is4xxClientError();
    }
}