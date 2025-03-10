package com.example.S2_H1.controller;

import com.example.S2_H1.api.UserApi;
import com.example.S2_H1.request.user.UserCreateRequest;
import com.example.S2_H1.request.user.UserUpdateDataRequest;
import com.example.S2_H1.request.user.UserUpdateNameDto;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.response.user.UserIdResponse;
import com.example.S2_H1.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CircuitBreaker(name = "apiCircuitBreaker")
public class UserController implements UserApi {
  private final UserService userService;

  @Override
  public ResponseEntity<UserIdResponse> registerUser(UserCreateRequest userCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userCreateRequest));
  }

  @Override
  public ResponseEntity<Void> deleteUser(Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<User> updateUserName(UserUpdateNameDto userUpdateNameDto, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserName(userUpdateNameDto, userId));
  }

  @Override
  public ResponseEntity<User> updateUserData(UserUpdateDataRequest userUpdateDataRequest, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserData(userUpdateDataRequest, userId));
  }
}
