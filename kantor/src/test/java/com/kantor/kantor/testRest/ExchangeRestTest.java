package com.kantor.kantor.testRest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class ExchangeRestTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API = "http://localhost:8089/api/exchange";

    @Test
    void testGetRate() {
        String url = API + "/rate?from=PLN&to=USD";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testConvert() {
        String url = API + "/convert?from=PLN&to=USD&amount=100";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testUpdateRate() {
        String url = API + "/rate?from=PLN&to=USD&rate=3.99";
        ResponseEntity<String> resp = restTemplate.postForEntity(url, null, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }
}