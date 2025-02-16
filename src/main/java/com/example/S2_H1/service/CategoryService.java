package com.example.S2_H1.service;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> findAll(Long userId) {
    log.info("Получение всех категорий юзера с айди {}", userId);
    return categoryRepository.findAll(new UserId(userId));
  }

  public Category findById(Long categoryId) {
    log.info("Получение категории с айди {}", categoryId);
    return categoryRepository.findById(new CategoryId(categoryId));
  }

  public void deleteCategory(Long categoryId) {
    log.info("Удаление категории с айди {}", categoryId);
    categoryRepository.deleteByCategoryId(new CategoryId(categoryId));
  }

  public void deleteAllUserCategories(Long userId) {
    log.info("Удаление всех категорий юзера {}", userId);
    categoryRepository.deleteByUserId(new UserId(userId));
  }

  public CategoryId create(String name, Long userId) {
    log.info("Создание категории {} для пользователя {}", name, userId);
    Category category = Category.builder().name(name).userId(new UserId(userId)).build();
    log.info("Категория создана");
    return categoryRepository.save(category);
  }
}
