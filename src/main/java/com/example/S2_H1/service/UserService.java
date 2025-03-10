package com.example.S2_H1.service;

import com.example.S2_H1.request.user.UserCreateRequest;
import com.example.S2_H1.request.user.UserUpdateDataRequest;
import com.example.S2_H1.request.user.UserUpdateNameDto;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.repository.UserRepository;
import com.example.S2_H1.response.user.UserIdResponse;
import com.example.S2_H1.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final Map<Long, String> userNames = new ConcurrentHashMap<>();

  @Transactional
  public UserIdResponse registerUser(UserCreateRequest userCreateRequest) {
    log.info("Создание нового пользователя");

    User user = new User(userCreateRequest.getUserName(), userCreateRequest.getUserPassword(), userCreateRequest.getUserEmail());
    log.info("Пользователь создан");

    userRepository.save(user);
    log.info("Пользователь сохранён");

    userNames.put(user.getUserId(), user.getUserName());
    return new UserIdResponse(user.getUserId());
  }

  @Transactional
  public void deleteUser(Long userId) {
    log.info("Удаление пользователя с айди {}", userId);

    if (userRepository.existsById(userId)) {
      log.info("Пользователь с id {} удалён из репозитория", userId);
      userNames.remove(userId);
    } else {
      log.info("Пользователь с айди {} не был найден", userId);
      throw new NoSuchUserException("Пользователь с id " + userId + " не найден");
    }
  }

  //Exactly Once
  @Transactional
  public User updateUserName(UserUpdateNameDto userUpdateNameDto, Long userId) {
    String newUserName = userUpdateNameDto.getNewUserName();

    if (userNames.get(userId).equals(newUserName)) {
      log.info("Новое имя пользователя совпадает с текущим");
      return getUserById(userId);
    }
    log.info("Обновление имени юзера с айди {}, на {}", userId, newUserName);

    User user = getUserById(userId);
    user.setUserName(newUserName);
    log.info("Имя пользователя обновлено");

    userNames.put(userId, newUserName);
    userRepository.save(user);
    log.info("Изменение имени пользователя {} сохранено в репозиторий", userId);

    return user;
  }

  @Transactional
  public User updateUserData(UserUpdateDataRequest userUpdateDataRequest, Long userId) {
    log.info("Обновление данных юзера с айди {}", userId);

    User user = getUserById(userId);
    user.setUserName(userUpdateDataRequest.getUserName());
    user.setUserPassword(userUpdateDataRequest.getUserPassword());
    user.setUserEmail(userUpdateDataRequest.getUserEmail());
    log.info("Данные пользователя обновлены");

    userRepository.save(user);
    log.info("Изменения данных пользователя {} сохранены в репозиторий", userId);

    userNames.put(userId, userUpdateDataRequest.getUserName());
    return user;
  }

  @Transactional(readOnly = true)
  private User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NoSuchUserException("Пользователь с id " + userId + " не найден"));
  }
}
