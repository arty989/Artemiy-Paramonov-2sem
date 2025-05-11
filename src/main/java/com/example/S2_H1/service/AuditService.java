package com.example.S2_H1.service;

import com.example.S2_H1.dto.AuditMessageDto;
import com.example.S2_H1.entity.OutboxRecord;
import com.example.S2_H1.enums.Action;
import com.example.S2_H1.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  @Transactional
  public void saveAuditMessage(Long userId, Action action, String eventDetails) {
    try {
      String auditMessage = objectMapper.writeValueAsString(
        AuditMessageDto.builder()
          .userId(userId)
          .eventTime(Instant.now())
          .eventType(action)
          .eventDetails(eventDetails)
        .build()
      );

      outboxRepository.save(new OutboxRecord(auditMessage));

    } catch (JsonProcessingException e) {

      log.error("Ошибка сериализации события аудита", e);
      throw new RuntimeException("Не удалось сериализовать событие аудита", e);

    }
  }
}