package com.example.S2_H1.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("db-test")
@Testcontainers
@Slf4j
public class SpringDatabaseTest {

  @Container
  public static PostgreSQLContainer<?> postgresContainer =
    new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("testdb")
      .withUsername("testuser")
      .withPassword("testpass")
      .withInitScript("db-init.sql");


  @DynamicPropertySource
  public static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @BeforeAll
  public static void logDatabaseInfo() {
    log.info("Контейнер запущен на {}:{}", postgresContainer.getHost(), postgresContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT));
    log.info("JDBC URL: {}", postgresContainer.getJdbcUrl());
  }

  @Test
  public void loggingPort() {
    assertTrue(postgresContainer.isRunning());
  }
}
