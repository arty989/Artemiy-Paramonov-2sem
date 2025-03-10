//package com.example.S2_H1.controller;
//
//import com.example.S2_H1.repository.exception.UserNotFoundException;
//import com.example.S2_H1.service.exception.NoSuchUserException;
//import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
//import io.github.resilience4j.ratelimiter.RequestNotPermitted;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//  @ExceptionHandler({UserNotFoundException.class, NoSuchUserException.class})
//  public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//  }
//
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> handleGlobalException(Exception ex) {
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла непредвиденная ошибка: " + ex.getMessage());
//  }
//
//  @ExceptionHandler(RequestNotPermitted.class)
//  public ResponseEntity<String> handleRateLimiterException(RequestNotPermitted ex) {
//    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Слишком много запросов, попробуйте позже");
//  }
//
//  @ExceptionHandler(CallNotPermittedException.class)
//  public ResponseEntity<String> handleCircuitBreakerException(CallNotPermittedException ex) {
//    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Сервис временно недоступен, попробуйте позже");
//  }
//}
