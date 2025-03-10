package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

  private final AtomicLong nextCategoryId = new AtomicLong(0);
  private final List<Category> categories = new ArrayList<>();

  private CategoryId getNewCategoryId() {
    return new CategoryId(nextCategoryId.incrementAndGet());
  }

  @Override
  public List<Category> findAll(UserId userId) {
    List<Category> answerCategory = new ArrayList<>();
    for (Category category : categories) {
      if (category.getUserId().equals(userId)) {
        answerCategory.add(category);
      }
    }
    log.info("Репозиторий вернул все категории юзера {}", userId.id());
    return answerCategory;
  }

  @Override
  public Category findById(CategoryId categoryId) {
    for (Category category : categories) {
      if (category.getCategoryId().equals(categoryId)) {
        log.info("Категория с айди {} найдена в репозитории", categoryId.id());
        return category;
      }
    }
    log.info("Категория с айди {} не найдена в репозитории", categoryId.id());
    return null;
  }

  @Override
  public void deleteByCategoryId(CategoryId categoryId) {
    for (Category category : categories) {
      if (category.getCategoryId().equals(categoryId)) {
        log.info("Категория с айди {} успешно удалена из репозитория", categoryId.id());
        categories.remove(category);
      }
    }
    log.info("Категория с айди {} не найдена в репозитории", categoryId.id());
  }

  @Override
  public void deleteByUserId(UserId userId) {
    for (Category category : categories) {
      if (category.getUserId().equals(userId)) {
        log.info("Категория с айди {} успешно удалена из репозитория", category.getCategoryId().id());
        categories.remove(category);
      }
    }
  }

  @Override
  public CategoryId save(Category category) {
    CategoryId categoryId = getNewCategoryId();
    category.setCategoryId(categoryId);
    categories.add(category);
    log.info("Категория {} с айди {} успешно добавлена в репозиторий пользователю с айди {}", category.getName(), categoryId.id(), category.getUserId());
    return category.getCategoryId();
  }

  @Override
  public void saveWithoutIdUpdate(Category category) {
    categories.add(category);
    log.info("Категория с айди {} успешно добавлена в репозиторий без изменения айди", category.getCategoryId());
  }
}