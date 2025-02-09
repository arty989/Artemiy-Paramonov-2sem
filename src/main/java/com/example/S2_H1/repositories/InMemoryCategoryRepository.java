package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    return answerCategory;
  }

  @Override
  public Category findById(CategoryId id) {
    return categories.stream()
    .filter(category -> category.getCategoryId().equals(id))
    .findFirst()
    .orElse(null);
  }

  @Override
  public void deleteByCategoryId(CategoryId id) {
    categories.removeIf(category -> category.getCategoryId().equals(id));
  }

  @Override
  public void deleteByUserId(UserId userId) {
    categories.removeIf(category -> category.getUserId().equals(userId));
  }

  @Override
  public CategoryId create(String name, UserId userId) {
    Category category = Category.builder().name(name).userId(userId).categoryId(getNewCategoryId()).build();
    categories.add(category);
    return category.getCategoryId();
  }
}