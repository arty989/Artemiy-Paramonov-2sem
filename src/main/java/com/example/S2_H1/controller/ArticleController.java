package com.example.S2_H1.controller;


import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
  private final ArticleService articleService;

  @GetMapping("/{userId}")
  public ResponseEntity<Map<Article, Category>> getArticlesForUser(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticles(userId));
  }
}
