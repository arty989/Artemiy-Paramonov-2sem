package com.example.S2_H1.controllers;

import com.example.S2_H1.entity.UserDto;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @PostMapping("/register/{userName}/{password}")
  public UserId registerUser(@RequestBody UserDto userDto) {
    return userService.registerUser(userDto);
  }

  @DeleteMapping("/delete/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
  }
}
