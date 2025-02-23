package com.example.S2_H1.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Endpoint(id = "custom")
public class UuidActuatorEndpoint {

  @ReadOperation
  public UUID randomUuid() {
    return UUID.randomUUID();
  }
}
