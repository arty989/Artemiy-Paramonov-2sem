package com.example.S2_H1.services;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.ArticleRepository;
import com.example.S2_H1.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;

  public Map<Article, Category> getArticles(Long userId) {
    return articleRepository.getArticles(new UserId(userId), categoryRepository);
  }
}
