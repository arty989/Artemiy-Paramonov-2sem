package com.example.S2_H1.services;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.ArticleRepository;
import com.example.S2_H1.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;

  public Map<Article, Category> getArticles(Long userId) {
    List<Article> articles = articleRepository.getArticles();
    List<Category> userCategories = categoryRepository.findAll(new UserId(userId));
    Map<Article, Category> articlesForCategories = new HashMap<>();
    //Рандомный вывод
    for (Category category : userCategories) {
      articlesForCategories.put(articles.get(0), category);
    }
    return articlesForCategories;
  }
}
