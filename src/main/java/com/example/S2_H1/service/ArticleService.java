package com.example.S2_H1.service;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.ArticleRepository;
import com.example.S2_H1.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;

  @Async
  public CompletableFuture<Map<Article, Category>> getArticles(Long userId) {
    log.info("Получение текущих статей");
    List<Article> articles = articleRepository.getArticles();
    log.info("Получение категорий для юзера {}", userId);
    List<Category> userCategories = categoryRepository.findAll(new UserId(userId));
    Map<Article, Category> articlesForCategories = new HashMap<>();
    //Рандомный вывод
    log.info("Сопоставление статей категориям");
    for (Category category : userCategories) {
      articlesForCategories.put(articles.get(0), category);
    }
    log.info("Вернули юзеру список статей и категорий, к которым они относятся");
    return CompletableFuture.completedFuture(articlesForCategories);
  }
}
