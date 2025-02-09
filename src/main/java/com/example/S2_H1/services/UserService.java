package com.example.S2_H1.services;

import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserDto;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.CategoryRepository;
import com.example.S2_H1.repositories.SiteRepository;
import com.example.S2_H1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final SiteRepository siteRepository;

  public UserId registerUser(UserDto userDto) {
    LOG.info("Создание нового пользователя");
    User user = User.builder().userName(userDto.getUserName()).password(userDto.getUserPassword()).build();
    LOG.info("Пользователь создан");
    return userRepository.saveAccount(user);
  }

  public void deleteUser(Long parsUserId) {
    LOG.info("Удаление пользователя с айди {}", parsUserId);
    UserId userId = new UserId(parsUserId);
    userRepository.deleteAccount(userId);
    LOG.info("Удаление категорий пользователя с айди {}", parsUserId);
    categoryRepository.deleteByUserId(userId);
    LOG.info("Удаление сайтов пользователя с айди {}", parsUserId);
    siteRepository.deleteByUserId(userId);
  }
}
