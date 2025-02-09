package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {
  private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);

  private final List<User> users = new ArrayList<>();
  private final AtomicLong nextUserId = new AtomicLong(0);

  private UserId generateId() {
    return new UserId(nextUserId.incrementAndGet());
  }

  @Override
  public UserId saveAccount(User user) {
    UserId userId = generateId();
    user.setUserId(userId);
    users.add(user);
    LOG.info("Юзер с айди {} успешно добавлен в репозиторий", user.getUserId().id());
    return userId;
  }

  @Override
  public void deleteAccount(UserId userId) {
    for (User user : users) {
      if (user.getUserId().equals(userId)) {
        users.remove(user);
        LOG.info("Юзер с айди {} успешно удалён из репозитория", user.getUserId().id());
        break;
      }
    }
  }
}
