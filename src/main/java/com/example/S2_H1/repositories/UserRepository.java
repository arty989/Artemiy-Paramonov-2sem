package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserId;

public interface UserRepository {
  UserId saveAccount(User user);
  void deleteAccount(UserId userId);
}