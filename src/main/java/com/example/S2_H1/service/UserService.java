package com.example.S2_H1.service;

import com.example.S2_H1.dto.UserUpdateNameDto;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.dto.UserDto;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.CategoryRepository;
import com.example.S2_H1.repository.SiteRepository;
import com.example.S2_H1.repository.UserRepository;
import com.example.S2_H1.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final SiteRepository siteRepository;

  private final Map<Long, String> userNames = new ConcurrentHashMap<>();

  public UserId registerUser(UserDto userDto) {
    log.info("Создание нового пользователя");
    User user = User.builder().userName(userDto.getUserName()).password(userDto.getUserPassword()).build();
    log.info("Пользователь создан");
    UserId userId = userRepository.saveAccount(user);
    userNames.put(userId.id(), user.getUserName());
    return userId;
  }

  public void deleteUser(Long parsUserId) {
    log.info("Удаление пользователя с айди {}", parsUserId);
    UserId userId = new UserId(parsUserId);
    try {
      userRepository.deleteAccount(userId);
      userNames.remove(userId.id());
      log.info("Удаление категорий пользователя с айди {}", parsUserId);
      categoryRepository.deleteByUserId(userId);
      log.info("Удаление сайтов пользователя с айди {}", parsUserId);
      siteRepository.deleteByUserId(userId);
    } catch (Exception e) {
      log.info("Пользователь с айди {} не был найден", parsUserId);
      throw new NoSuchUserException("Данный пользователь не найден", e);
    }
  }

  //Exactly Once
  public User updateUserName(UserUpdateNameDto userUpdateNameDto, Long parsUserId) {
    String newUserName = userUpdateNameDto.getNewUserName();
    UserId userId = new UserId(parsUserId);
    if (userNames.get(parsUserId).equals(newUserName)) {
      log.info("Новое имя пользователя совпадает с текущим");
      return userRepository.getUser(userId);
    }
    log.info("Обновление имени юзера с айди {}, на {}", parsUserId, newUserName);
    User userWithOldName = userRepository.getUser(userId);
    User userWithUpdatedName = User.builder().userId(userId).password(userWithOldName.getPassword()).userName(newUserName).build();
    log.info("Имя пользователя обновлено");
    userRepository.deleteAccount(userId);
    userRepository.saveUserWithoutIdUpdate(userWithUpdatedName);
    userNames.put(parsUserId, userWithUpdatedName.getUserName());
    log.info("Изменения сохранены в репозиторий");
    return userWithUpdatedName;
  }

  public User updateUserData(UserDto userDto, Long parsUserId) {
    UserId userId = new UserId(parsUserId);
    log.info("Обновление данных юзера с айди {}", parsUserId);
    userRepository.deleteAccount(userId);
    User userWithUpdatedData = User.builder().userId(userId).userName(userDto.getUserName()).password(userDto.getUserPassword()).build();
    log.info("Данные пользователя обновлены");
    userRepository.saveUserWithoutIdUpdate(userWithUpdatedData);
    userNames.put(parsUserId, userWithUpdatedData.getUserName());
    log.info("Изменения сохранены в репозиторий");
    return userWithUpdatedData;
  }
}
