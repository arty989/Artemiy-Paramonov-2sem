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

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final SiteRepository siteRepository;

  public UserId registerUser(UserDto userDto) {
    log.info("Создание нового пользователя");
    User user = User.builder().userName(userDto.getUserName()).password(userDto.getUserPassword()).build();
    log.info("Пользователь создан");
    return userRepository.saveAccount(user);
  }

  public void deleteUser(Long parsUserId) {
    log.info("Удаление пользователя с айди {}", parsUserId);
    UserId userId = new UserId(parsUserId);
    try {
      userRepository.deleteAccount(userId);
    log.info("Удаление категорий пользователя с айди {}", parsUserId);
    categoryRepository.deleteByUserId(userId);
    log.info("Удаление сайтов пользователя с айди {}", parsUserId);
    siteRepository.deleteByUserId(userId);
    } catch (Exception e) {
      log.info("Пользователь с айди {} не был найден", parsUserId);
      throw new NoSuchUserException("Данный пользователь не найден", e);
    }
  }

  public User updateUserName(UserUpdateNameDto userUpdateNameDto, Long parsUserId) {
    String newUserName = userUpdateNameDto.getNewUserName();
    UserId userId = new UserId(parsUserId);
    log.info("Обновление имени юзера с айди {}, на {}", parsUserId, newUserName);
    User userWithOldName = userRepository.getUser(userId);
    User userWithUpdatedName = User.builder().userId(userId).password(userWithOldName.getPassword()).userName(newUserName).build();
    log.info("Имя пользователя обновлено");
    userRepository.deleteAccount(userId);
    userRepository.saveUserWithoutIdUpdate(userWithUpdatedName);
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
    log.info("Изменения сохранены в репозиторий");
    return userWithUpdatedData;
  }
}
