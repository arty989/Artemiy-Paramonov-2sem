package com.example.S2_H1.services;

import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserDto;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.CategoryRepository;
import com.example.S2_H1.repositories.SiteRepository;
import com.example.S2_H1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final SiteRepository siteRepository;

  public UserId registerUser(UserDto userDto) {
    User user = User.builder().userName(userDto.getUserName()).password(userDto.getUserPassword()).build();
    return userRepository.saveAccount(user);
  }

  public void deleteUser(Long parsUserId) {
    UserId userId = new UserId(parsUserId);
    userRepository.deleteAccount(userId);
    categoryRepository.deleteByUserId(userId);
    siteRepository.deleteByUserId(userId);
  }
}
