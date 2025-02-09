package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;

import java.util.List;

public interface CategoryRepository {
  List<Category> findAll(UserId userId);

  Category findById(CategoryId id);

  void deleteByCategoryId(CategoryId categoryId);

  void deleteByUserId(UserId userId);

  CategoryId save(Category category);
}