package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.ArticleId;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryArticleRepository implements ArticleRepository {
  //Т.к. реализация без парсера, то просто рандомную статью беру.
  private final AtomicLong nextArticleId = new AtomicLong(0);

  private ArticleId generateId() {
    return new ArticleId(nextArticleId.incrementAndGet());
  }

  @Override
  public Map<Article, Category> getArticles(UserId userId, CategoryRepository categoryRepository) {
    List<Category> userCategories = categoryRepository.findAll(userId);
    Map<Article, Category> answerArticles = new HashMap<>();
    //Рандомный вывод
    for (Category category : userCategories) {
      answerArticles.put(Article.builder().id(generateId()).name("МТС").build(), category);
    }
    return answerArticles;
  }
}
