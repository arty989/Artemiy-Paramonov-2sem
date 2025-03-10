package com.example.S2_H1.repository;


import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class InMemoryUserRepository implements UserRepository {

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
    log.info("Юзер с айди {} успешно добавлен в репозиторий", user.getUserId().id());
    return userId;
  }

  @Override
  public void deleteAccount(UserId userId) {
    for (User user : users) {
      if (user.getUserId().equals(userId)) {
        users.remove(user);
        log.info("Юзер с айди {} успешно удалён из репозитория", user.getUserId().id());
        return;
      }
    }
    throw new UserNotFoundException("Пользователь с id " + userId.id() + " отсутствует в репозитории");
  }

  @Override
  public User getUser(UserId userId) {
    for (User user : users) {
      if (user.getUserId().equals(userId)) {
        log.info("Юзер с айди {} найден", user.getUserId().id());
        return user;
      }
    }
    throw new RuntimeException();
  }

  @Override
  public void saveUserWithoutIdUpdate(User user) {
    users.add(user);
    log.info("Юзер с айди {} успешно добавлен в репозиторий без изменения айди", user.getUserId().id());
  }
}
