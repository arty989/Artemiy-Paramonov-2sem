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

  public List<Category> findAll(Long userId) {
    return categoryRepository.findAll(new UserId(userId));
  }

  public Category findById(Long categoryId) {
    return categoryRepository.findById(new CategoryId(categoryId));
  }

  public void deleteCategory(Long categoryId) {
    categoryRepository.deleteByCategoryId(new CategoryId(categoryId));
  }

  public void deleteAllUserCategories(Long userId) {
    categoryRepository.deleteByUserId(new UserId(userId));
  }

  public CategoryId create(String name, Long userId) {
    return categoryRepository.create(name, new UserId(userId));
  }
}
