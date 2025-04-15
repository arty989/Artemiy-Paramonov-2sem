package com.example.S2_H1.service;


import com.example.S2_H1.dto.AuditMessageDto;
import com.example.S2_H1.enums.Action;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(
  classes = {AuditProducerService.class},
  properties = {"topic-to-send-message=test-topic"}
)
@Import({KafkaAutoConfiguration.class, AuditProducerServiceTest.ObjectMapperTestConfig.class})
@Testcontainers
public class AuditProducerServiceTest {

  @TestConfiguration
  static class ObjectMapperTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      return objectMapper;
    }
  }

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @Autowired
  private AuditProducerService auditProducerService;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldSendMessageToKafkaSuccessfully() {
    AuditMessageDto testDtoMessage = AuditMessageDto.builder()
        .userId(1L)
        .eventTime(Instant.now())
        .eventType(Action.INSERT)
        .eventDetails("Пустота")
    .build();

    assertDoesNotThrow(() -> auditProducerService.sendAuditMessage(testDtoMessage));

    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "test-group-id");
    consumer.subscribe(List.of("test-topic"));

    ConsumerRecords<String, String> records = consumer.poll();
    assertThat(1).isEqualTo(records.count());
    records.iterator().forEachRemaining(
      record -> {
        try {
          AuditMessageDto message = objectMapper.readValue(record.value(), AuditMessageDto.class);
          assertThat(testDtoMessage).isEqualTo(message);
        } catch (Exception e) {
          fail("Ошибка десериализации JSON: " + e.getMessage());
        }
      }
    );
  }

  @Test
  void shouldFailToSendMessageDueToSerializationError() {
    ObjectMapper brokenObjectMapper = new ObjectMapper();

    AuditProducerService serviceWithBrokenMapper = new AuditProducerService(
      auditProducerService.getKafkaTemplate(),
      brokenObjectMapper
    );

    AuditMessageDto testDtoMessage = AuditMessageDto.builder()
      .userId(1L)
      .eventTime(Instant.now())
      .eventType(Action.INSERT)
      .eventDetails("Пустота")
    .build();

    assertThatThrownBy(() -> serviceWithBrokenMapper.sendAuditMessage(testDtoMessage))
      .isInstanceOf(JsonProcessingException.class)
      .hasMessageContaining("Java 8 date/time type `java.time.Instant` not supported");
  }

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
