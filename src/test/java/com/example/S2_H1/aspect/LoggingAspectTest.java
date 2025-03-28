package com.example.S2_H1.aspect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoggingAspectTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private LoggingAspect loggingAspect;


  @BeforeEach
  public void resetCounter() {
    loggingAspect.setCounter(0);
  }

  @Test
  public void testAspectIncreasesCounterByTwo() {
    ResponseEntity<Map> response = restTemplate.getForEntity("http://localhost:" + port + "/api/sites", Map.class);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(2, loggingAspect.getCounter());
  }
}