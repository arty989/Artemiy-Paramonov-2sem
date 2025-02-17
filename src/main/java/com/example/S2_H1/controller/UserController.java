package com.example.S2_H1.controller;

import com.example.S2_H1.api.UserApi;
import com.example.S2_H1.dto.UserDto;
import com.example.S2_H1.dto.UserUpdateNameDto;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {
  private final UserService userService;

  @Override
  public ResponseEntity<UserId> registerUser(UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(userDto));
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
  public ResponseEntity<User> updateUserData(UserDto userDto, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserData(userDto, userId));
  }
}
