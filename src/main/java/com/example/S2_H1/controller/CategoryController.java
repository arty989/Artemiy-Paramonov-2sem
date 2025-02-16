package com.example.S2_H1.controller;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.CategoryId;
import com.example.S2_H1.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Category>> getUserCategories(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(userId));
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
  }

  @DeleteMapping("/delete/{categoryId}")
  public ResponseEntity<Void> deleteCategoryById(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/delete/user/{userId}")
  public ResponseEntity<Void> deleteUserCategories(@PathVariable Long userId) {
    categoryService.deleteAllUserCategories(userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/create/{name}/{userId}")
  public ResponseEntity<CategoryId> createCategoryForUser(@PathVariable String name, @PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(name, userId));
  }

}
