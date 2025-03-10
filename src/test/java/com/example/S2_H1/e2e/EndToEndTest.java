package com.example.S2_H1.e2e;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EndToEndTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testGetAllAvailableSites() {
    ResponseEntity<Map> response = restTemplate.getForEntity("http://localhost:" + port + "/api/sites", Map.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map sites = response.getBody();
    assertNotNull(sites);
    assertTrue(sites.containsValue("https://habr.com/ru/news/"));
  }
}
