package com.example.S2_H1.service.exception;

public class NoSuchArticleException extends RuntimeException {
  public NoSuchArticleException(String message) {
    super(message);
  }
}
