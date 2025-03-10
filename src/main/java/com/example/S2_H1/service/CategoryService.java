package com.example.S2_H1.service;

import com.example.S2_H1.dto.CategoryDto;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

  @Cacheable(value = "categories", key = "#categoryId")
  public Category findById(Long categoryId) {
    log.info("Получение категории с айди {}", categoryId);
    return categoryRepository.findById(new CategoryId(categoryId));
  }

  @CacheEvict(value = "categories", key = "#categoryId")
  public void deleteCategory(Long categoryId) {
    log.info("Удаление категории с айди {}", categoryId);
    categoryRepository.deleteByCategoryId(new CategoryId(categoryId));
  }

  @CacheEvict(value = "categories", allEntries = true)
  public void deleteAllUserCategories(Long userId) {
    log.info("Удаление всех категорий юзера {}", userId);
    categoryRepository.deleteByUserId(new UserId(userId));
  }

  public CategoryId create(CategoryDto categoryDto, Long userId) {
    log.info("Создание категории {} для пользователя {}", categoryDto.getCategoryName(), userId);
    Category category = Category.builder().name(categoryDto.getCategoryName()).userId(new UserId(userId)).build();
    log.info("Категория создана");
    return categoryRepository.save(category);
  }

  @CachePut(value = "categories", key = "#categoryId")
  public Category updateCategoryData(CategoryDto categoryDto, Long categoryId) {
    CategoryId needTypeCategoryId = new CategoryId(categoryId);
    log.info("Обновление данных категории с айди {}", categoryId);
    Category categoryWithOutdateData = categoryRepository.findById(needTypeCategoryId);
    log.info("Получена категория с устаревшими данными");
    Category updatedCategory = Category.builder().categoryId(needTypeCategoryId).userId(categoryWithOutdateData.getUserId()).name(categoryDto.getCategoryName()).build();
    log.info("Данные категории обновлены");
    categoryRepository.deleteByCategoryId(needTypeCategoryId);
    categoryRepository.saveWithoutIdUpdate(updatedCategory);
    log.info("Обновления сохранены в репозиторий");
    return updatedCategory;
  }
}
