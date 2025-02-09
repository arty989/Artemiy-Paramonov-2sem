package com.example.S2_H1.controllers;

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
  public UserId registerUser(@PathVariable String userName, @PathVariable String password) {
    return userService.registerUser(userName, password);
  }

  @DeleteMapping("/delete/{userId}")
  public void deleteUser(@PathVariable UserId userId) {
    userService.deleteUser(userId);
  }
}
