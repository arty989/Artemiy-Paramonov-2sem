package com.example.S2_H1.metric;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMetrics {

  private final MeterRegistry registry;

  public void recordSendTime(String exceptionName, double durationInMilliseconds) {
    DistributionSummary outboxKafkaSendSummary = DistributionSummary
      .builder("outbox.kafka.send.time")
      .description("Время, затраченное на отправку записей из outbox в Kafka")
      .baseUnit("milliseconds")
      .serviceLevelObjectives(1, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000, 5000, 10000)
      .tag("exception", exceptionName)
    .register(registry);
    outboxKafkaSendSummary.record(durationInMilliseconds);
  }
}
