package com.kantor.kantor.testRest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class SystemLogRestTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API = "http://localhost:8089/api/logs";

    @Test
    void testUserLogs() {
        String url = API + "/user?username=user";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testAllLogs() {
        String url = API + "/all";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testUserLogsNotFound() {
        String url = API + "/user?username=brakTegoUsera";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        assert resp.getStatusCode().is4xxClientError();
    }
}