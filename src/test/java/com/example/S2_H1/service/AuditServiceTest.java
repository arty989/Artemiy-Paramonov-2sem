package com.example.S2_H1.service;

import com.example.S2_H1.dto.AuditMessageDto;
import com.example.S2_H1.entity.OutboxRecord;
import com.example.S2_H1.enums.Action;
import com.example.S2_H1.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuditServiceTest {
  @Autowired
  private AuditService auditService;

  @Autowired
  private OutboxRepository outboxRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @BeforeEach
  void setUp() {
    outboxRepository.deleteAll();
  }

  @Test
  void shouldSaveAuditMessageSuccessfully() throws JsonProcessingException {
    Long userId = 1L;
    Action action = Action.INSERT;
    String eventDetails = "Подробности";

    auditService.saveAuditMessage(userId, action, eventDetails);

    List<OutboxRecord> records = outboxRepository.findAll();
    assertThat(records).hasSize(1);

    OutboxRecord savedRecord = records.get(0);
    assertThat(savedRecord.getData()).contains("userId");
    assertThat(savedRecord.getData()).contains("eventType");
    assertThat(savedRecord.getData()).contains("eventDetails");

    AuditMessageDto message = objectMapper.readValue(savedRecord.getData(), AuditMessageDto.class);
    assertThat(message.getUserId()).isEqualTo(userId);
    assertThat(message.getEventType()).isEqualTo(action);
    assertThat(message.getEventDetails()).isEqualTo(eventDetails);
  }

  @Test
  void shouldFailSaveAuditMessageDueToSerializationError() {
    Long userId = 1L;
    Action action = Action.INSERT;
    String eventDetails = "Подробности";

    ObjectMapper brokenObjectMapper = new ObjectMapper();
    AuditService auditServiceWithBrokenMapper = new AuditService(outboxRepository, brokenObjectMapper);

    assertThatThrownBy(() -> auditServiceWithBrokenMapper.saveAuditMessage(userId, action, eventDetails))
      .isInstanceOf(RuntimeException.class)
      .hasMessageContaining("Не удалось сериализовать событие аудита");
  }
}
