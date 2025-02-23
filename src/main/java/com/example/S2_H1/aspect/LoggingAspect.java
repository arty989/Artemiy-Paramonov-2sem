package com.example.S2_H1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class LoggingAspect {
  @Before("execution(* com.example.S2_H1.controller.*.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    System.out.println("Перед вызовом метода: " + joinPoint.getSignature().getName());
  }

  @Around("execution(* com.example.S2_H1.controller.*.*(..))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant startTime = Instant.now();
    Object result = joinPoint.proceed();
    Instant endTime = Instant.now();
    Duration duration = Duration.between(startTime, endTime);  // Разница времени
    System.out.println(
      "Метод " + joinPoint.getSignature().getName() + " выполнен за " + duration.toMillis() + " мс");
    return result;
  }
}
