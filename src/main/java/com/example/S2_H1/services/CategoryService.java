package com.example.S2_H1.services;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
  private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

  private final CategoryRepository categoryRepository;

  public List<Category> findAll(Long userId) {
    LOG.info("Получение всех категорий юзера с айди {}", userId);
    return categoryRepository.findAll(new UserId(userId));
  }

  public Category findById(Long categoryId) {
    LOG.info("Получение категории с айди {}", categoryId);
    return categoryRepository.findById(new CategoryId(categoryId));
  }

  public void deleteCategory(Long categoryId) {
    LOG.info("Удаление категории с айди {}", categoryId);
    categoryRepository.deleteByCategoryId(new CategoryId(categoryId));
  }

  public void deleteAllUserCategories(Long userId) {
    LOG.info("Удаление всех категорий юзера {}", userId);
    categoryRepository.deleteByUserId(new UserId(userId));
  }

  public CategoryId create(String name, Long userId) {
    LOG.info("Создание категории {} для пользователя {}", name, userId);
    Category category = Category.builder().name(name).userId(new UserId(userId)).build();
    LOG.info("Категория создана");
    return categoryRepository.save(category);
  }
}
