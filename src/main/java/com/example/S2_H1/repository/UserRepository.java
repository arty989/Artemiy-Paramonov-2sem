package com.example.S2_H1.repository;

import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserId;

public interface UserRepository {
  UserId saveAccount(User user);
  void deleteAccount(UserId userId);
  User getUser(UserId userId);
  void saveUserWithoutIdUpdate(User user);
}