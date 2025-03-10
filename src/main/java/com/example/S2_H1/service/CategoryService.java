package com.example.S2_H1.service;

import com.example.S2_H1.entity.User;
import com.example.S2_H1.repository.UserRepository;
import com.example.S2_H1.request.category.CategoryCreateRequest;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.repository.CategoryRepository;
import com.example.S2_H1.request.category.CategoryUpdateDataRequest;
import com.example.S2_H1.response.category.CategoryIdResponse;
import com.example.S2_H1.response.category.CategoryResponse;
import com.example.S2_H1.service.exception.NoSuchCategoryException;
import com.example.S2_H1.service.exception.NoSuchSiteException;
import com.example.S2_H1.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<CategoryResponse> findAllUserCategories(Long userId) {
    log.info("Получение всех категорий юзера с айди {}", userId);

    User user = getUserById(userId);
    log.info("Пользователь найден, получаем его категории");

    List<CategoryResponse> responseCategories = new ArrayList<>();
    for (Category category : user.getCategories()) {
      responseCategories.add(new CategoryResponse(category));
    }

    return responseCategories;
  }

  @Transactional(readOnly = true)
  @Cacheable(value = "categories", key = "#categoryId")
  public CategoryResponse findById(Long categoryId) {
    log.info("Получение категории с айди {}", categoryId);
    return new CategoryResponse(getCategoryById(categoryId));
  }

  @Transactional
  @CacheEvict(value = "categories", key = "#categoryId")
  public void deleteCategory(Long categoryId) {
    log.info("Удаление категории с айди {}", categoryId);

    if (categoryRepository.existsById(categoryId)) {

      categoryRepository.deleteById(categoryId);
      log.info("Категория с id {} удалена из репозитория", categoryId);

    } else {

      log.info("Категория с id {} не была найдена", categoryId);
      throw new NoSuchCategoryException("Категория с id " + categoryId + " не найден");
    }
  }

  @Transactional
  @CacheEvict(value = "categories", allEntries = true)
  public void deleteAllUserCategories(Long userId) {
    log.info("Удаление всех категорий юзера {}", userId);
    categoryRepository.deleteByUserUserId(userId);
  }

  @Transactional
  public CategoryIdResponse createCategoryForUser(Long userId, CategoryCreateRequest categoryCreateRequest) {
    log.info("Создание категории {} для пользователя {}", categoryCreateRequest.getCategoryName(), userId);

    User user = getUserById(userId);
    log.info("Пользователь найден");

    Category category = new Category(categoryCreateRequest.getCategoryName(), user);
    log.info("Категория создана");

    categoryRepository.save(category);
    log.info("Категория сохранена в репозитории");

    user.addCategory(category);

    userRepository.save(user);
    return new CategoryIdResponse(category.getCategoryId());
  }

  @Transactional
  @CachePut(value = "categories", key = "#categoryId")
  public CategoryResponse updateCategoryData(Long categoryId, CategoryUpdateDataRequest categoryUpdateDataRequest) {
    log.info("Обновление данных категории с айди {}", categoryId);

    Category category = getCategoryById(categoryId);
    log.info("Получена категория с устаревшими данными");

    category.setCategoryName(categoryUpdateDataRequest.getCategoryName());
    log.info("Данные категории обновлены");

    categoryRepository.save(category);
    log.info("Обновления сохранены в репозиторий");
    return new CategoryResponse(category);
  }

  @Transactional(readOnly = true)
  private User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NoSuchUserException("Пользователь с id " + userId + " не найден"));
  }

  @Transactional(readOnly = true)
  private Category getCategoryById(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchCategoryException("Категория с id " + categoryId + " не найдена"));
  }
}
