package com.example.S2_H1.aspect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class LoggingAspectTest {
  @LocalServerPort
  private int port;

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

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
    ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/api/sites", List.class);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(2, loggingAspect.getCounter());
  }
}