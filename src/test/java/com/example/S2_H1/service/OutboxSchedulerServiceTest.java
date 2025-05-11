package com.example.S2_H1.service;

import com.example.S2_H1.dto.AuditMessageDto;
import com.example.S2_H1.enums.Action;
import com.example.S2_H1.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(
   properties = {"topic-to-send-message=test-topic"}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
public class OutboxSchedulerServiceTest {

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @Autowired
  private OutboxSchedulerService outboxSchedulerService;

  @Autowired
  private AuditService auditService;

  @Autowired
  private OutboxRepository outboxRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    outboxRepository.deleteAll();
  }

  @Test
  void shouldSendMessageToKafkaSuccessfully() {
    Long userId = 1L;
    Action action = Action.INSERT;
    String eventDetails = "Подробности";

    assertDoesNotThrow(() -> auditService.saveAuditMessage(userId, action, eventDetails));

    outboxSchedulerService.processOutbox();

    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "test-group-id");
    consumer.subscribe(List.of("test-topic"));

    ConsumerRecords<String, String> records = consumer.poll();

    assertThat(1).isEqualTo(records.count());

    records.iterator().forEachRemaining(
    record -> {
      try {
        assertThat(record.value())
        .contains("\"userId\":" + userId)
        .contains("\"eventType\":\"" + action + "\"")
        .contains("\"eventDetails\":\"" + eventDetails + "\"");

        AuditMessageDto message = objectMapper.readValue(record.value(), AuditMessageDto.class);
        assertThat(message.getUserId()).isEqualTo(userId);
        assertThat(message.getEventType()).isEqualTo(action);
        assertThat(message.getEventDetails()).isEqualTo(eventDetails);

        assertThat(outboxRepository.findAll().size()).isEqualTo(0);

      } catch (JsonProcessingException e) {
        fail("Ошибка десериализации JSON: " + e.getMessage());
      }
    }
    );
  }

  //Негативный тест в AuditServiceTest

  private static class KafkaTestConsumer {
    private final KafkaConsumer<String, String> consumer;

    public KafkaTestConsumer(String bootstrapServers, String groupId) {
      Properties props = new Properties();

      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

      this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribe(List<String> topics) {
      consumer.subscribe(topics);
    }

    public ConsumerRecords<String, String> poll() {
      return consumer.poll(Duration.ofSeconds(5));
    }
  }
}
