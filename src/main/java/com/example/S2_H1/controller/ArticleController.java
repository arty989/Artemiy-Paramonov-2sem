package com.example.S2_H1.controller;


import com.example.S2_H1.api.ArticleApi;
import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class ArticleController implements ArticleApi {
  private final ArticleService articleService;

  @Override
  public ResponseEntity<Map<Article, Category>> getArticlesForUser(Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticles(userId));
  }
}
