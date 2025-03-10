package com.example.S2_H1.service.exception;

public class NoSuchCategoryException extends RuntimeException {
  public NoSuchCategoryException(String message) {
    super(message);
  }
}