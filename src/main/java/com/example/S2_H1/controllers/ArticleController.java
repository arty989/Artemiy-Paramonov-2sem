package com.example.S2_H1.controllers;


import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.services.ArticleService;
import lombok.AllArgsConstructor;
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
  public Map<Article, Category> getArticlesForUser(@PathVariable Long userId) {
    return  articleService.getArticles(userId);
  }
}
