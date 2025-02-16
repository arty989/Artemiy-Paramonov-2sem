package com.example.S2_H1.service;

import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserDto;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.CategoryRepository;
import com.example.S2_H1.repository.SiteRepository;
import com.example.S2_H1.repository.UserRepository;
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
    userRepository.deleteAccount(userId);
    log.info("Удаление категорий пользователя с айди {}", parsUserId);
    categoryRepository.deleteByUserId(userId);
    log.info("Удаление сайтов пользователя с айди {}", parsUserId);
    siteRepository.deleteByUserId(userId);
  }
}
