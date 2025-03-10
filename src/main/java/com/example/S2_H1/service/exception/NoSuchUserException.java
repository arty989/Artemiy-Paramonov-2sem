package com.example.S2_H1.service.exception;

public class NoSuchUserException extends RuntimeException {
  public NoSuchUserException(String message) {
    super(message);
  }
}
