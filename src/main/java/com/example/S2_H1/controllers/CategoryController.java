package com.example.S2_H1.controllers;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("/user/{userId}")
  public List<Category> getUserCategories(@PathVariable Long userId) {
    return categoryService.findAll(userId);
  }

  @GetMapping("/{categoryId}")
  public Category getCategoryById(@PathVariable Long categoryId) {
    return categoryService.findById(categoryId);
  }

  @DeleteMapping("/delete/{categoryId}")
  public void deleteCategoryById(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
  }

  @DeleteMapping("/delete/user/{userId}")
  public void deleteUserCategories(@PathVariable Long userId) {
    categoryService.deleteAllUserCategories(userId);
  }

  @PostMapping("/create/{name}/{userId}")
  public CategoryId createCategoryForUser(@PathVariable String name, @PathVariable Long userId) {
    return categoryService.create(name, userId);
  }

}
