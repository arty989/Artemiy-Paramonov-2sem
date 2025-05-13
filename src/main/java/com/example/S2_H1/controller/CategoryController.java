package com.example.S2_H1.controller;

import com.example.S2_H1.api.CategoryApi;
import com.example.S2_H1.request.category.CategoryCreateRequest;
import com.example.S2_H1.request.category.CategoryUpdateDataRequest;
import com.example.S2_H1.response.category.CategoryIdResponse;
import com.example.S2_H1.response.category.CategoryResponse;
import com.example.S2_H1.service.CategoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RateLimiter(name = "apiRateLimiter")
@CircuitBreaker(name = "apiCircuitBreaker")
public class CategoryController implements CategoryApi {
  private final CategoryService categoryService;

  @Override
  public ResponseEntity<List<CategoryResponse>> getUserCategories(Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllUserCategories(userId));
  }

  @Override
  public ResponseEntity<CategoryResponse> getCategoryById(Long categoryId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(categoryId));
  }

  @Override
  public ResponseEntity<Void> deleteCategoryById(Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<Void> deleteUserCategories(Long userId) {
    categoryService.deleteAllUserCategories(userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<CategoryIdResponse> createCategoryForUser(CategoryCreateRequest categoryDto, Long userId) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategoryForUser(userId, categoryDto));
  }

  @Override
  public ResponseEntity<CategoryResponse> updateCategoryData(CategoryUpdateDataRequest categoryUpdateDataRequest, Long categoryId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategoryData(categoryId, categoryUpdateDataRequest));
  }
}
