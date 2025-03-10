package com.example.S2_H1.controller;

import com.example.S2_H1.entity.UserDto;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserId> registerUser(@RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(userDto));
  }

  @DeleteMapping("/delete/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
