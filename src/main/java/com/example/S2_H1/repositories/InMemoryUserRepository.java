package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {
  private final List<User> users = new ArrayList<>();
  private final AtomicLong nextUserId = new AtomicLong(0);

  public UserId generateId() {
    return new UserId(nextUserId.incrementAndGet());
  }

  @Override
  public void saveAccount(User user) {
    users.add(user);
  }

  @Override
  public void deleteAccount(UserId userId) {
    for (User user : users) {
      if (user.getUserId().equals(userId)) {
        users.remove(user);
        break;
      }
    }
  }
}
