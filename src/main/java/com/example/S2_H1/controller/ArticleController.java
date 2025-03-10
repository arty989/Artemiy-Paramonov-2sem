package com.example.S2_H1.controller;


import com.example.S2_H1.api.ArticleApi;
import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.response.article.ArticleResponse;
import com.example.S2_H1.response.category.CategoryResponse;
import com.example.S2_H1.service.ArticleService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RateLimiter(name = "apiRateLimiter")
public class ArticleController implements ArticleApi {
  private final ArticleService articleService;

  @Override
  public CompletableFuture<ResponseEntity<List<ArticleResponse>>> getArticlesForUser(Long userId) {
    return articleService.getArticles(userId).thenApply(articles -> ResponseEntity.status(HttpStatus.OK).body(articles));
  }
}
