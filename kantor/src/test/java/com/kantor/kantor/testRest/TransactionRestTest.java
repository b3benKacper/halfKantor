package com.kantor.kantor.testRest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class TransactionRestTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API = "http://localhost:8089/api/transactions";

    @Test
    void testExchange() {
        String url = API + "/exchange?username=user&from=PLN&to=USD&amount=10";
        ResponseEntity<String> resp = restTemplate.postForEntity(url, null, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testUserTransactionHistory() {
        String url = API + "/user?username=user";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testAllTransactions() {
        String url = API + "/all";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }
}