package com.example.S2_H1.service;

import com.example.S2_H1.entity.OutboxRecord;
import com.example.S2_H1.metric.OutboxMetrics;
import com.example.S2_H1.repository.OutboxRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class OutboxSchedulerService {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final OutboxRepository outboxRepository;

  private final OutboxMetrics outboxMetrics;

  @Value("${topic-to-send-message}")
  private String topic;

  @Transactional
  @Scheduled(fixedDelay = 10000)
  public void processOutbox() {
    List<OutboxRecord> result = outboxRepository.findAll();

    for (OutboxRecord outboxRecord : result) {
      long startTime = System.nanoTime();
      String exceptionName = "none";

      try {
        sendMessageAndDelete(outboxRecord);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        exceptionName = "InterruptedException";
        log.error("Неожиданное прерывание потока при отправке сообщения в kafka", e);
      } catch (ExecutionException e) {
        exceptionName = "ExecutionException";
        log.warn("Не удалось отправить сообщение в kafka", e);
      } catch (TimeoutException e) {
        exceptionName = "TimeoutException";
        log.warn("Не удалось отправить сообщение в kafka из-за превышения допустимого времени отправки", e);
      } catch (DataAccessException e) {
        exceptionName = "DataAccessException";
        log.error("Возникла проблема с удалением сообщения из outbox", e);
      } finally {
        long duration = System.nanoTime() - startTime;
        double durationInMilliseconds = (double) duration / 1_000_000.0;
        outboxMetrics.recordSendTime(exceptionName, durationInMilliseconds);
      }
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  private void sendMessageAndDelete(OutboxRecord outboxRecord) throws InterruptedException, ExecutionException, TimeoutException {
    SendResult<String, String> result = kafkaTemplate.send(topic, outboxRecord.getData()).get(2, TimeUnit.SECONDS);
    outboxRepository.delete(outboxRecord);
  }
}
