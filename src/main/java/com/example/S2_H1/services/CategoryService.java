package com.example.S2_H1.services;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public List<Category> findAll(UserId userId) {
    return categoryRepository.findAll(userId);
  }

  public Category findById(CategoryId id) {
    return categoryRepository.findById(id);
  }

  public void deleteCategory(CategoryId categoryId) {
    categoryRepository.deleteByCategoryId(categoryId);
  }

  public void deleteAllUserCategories(UserId userId) {
    categoryRepository.deleteByUserId(userId);
  }

  public CategoryId create(String name, UserId userId) {
    return categoryRepository.create(name, userId);
  }
}
