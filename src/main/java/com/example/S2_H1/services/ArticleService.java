package com.example.S2_H1.services;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.ArticleRepository;
import com.example.S2_H1.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ArticleService {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleService.class);

  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;

  public Map<Article, Category> getArticles(Long userId) {
    LOG.info("Получение текущих статей");
    List<Article> articles = articleRepository.getArticles();
    LOG.info("Получение категорий для юзера {}", userId);
    List<Category> userCategories = categoryRepository.findAll(new UserId(userId));
    Map<Article, Category> articlesForCategories = new HashMap<>();
    //Рандомный вывод
    LOG.info("Сопоставление статей категориям");
    for (Category category : userCategories) {
      articlesForCategories.put(articles.get(0), category);
    }
    LOG.info("Вернули юзеру список статей и категорий, к которым они относятся");
    return articlesForCategories;
  }
}
