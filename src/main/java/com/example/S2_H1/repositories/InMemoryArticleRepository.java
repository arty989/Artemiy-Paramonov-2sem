package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.ArticleId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryArticleRepository implements ArticleRepository {
  private static final Logger LOG = LoggerFactory.getLogger(InMemoryArticleRepository.class);

  //Т.к. реализация без парсера и мл, то статья здесь всегда одна, придуманная мной.
  private final AtomicLong nextArticleId = new AtomicLong(0);
  private final List<Article> articles = List.of(Article.builder().id(generateId()).name("МТС").build(), Article.builder().id(generateId()).name("МФТИ").build());

  private ArticleId generateId() {
    return new ArticleId(nextArticleId.incrementAndGet());
  }

  @Override
  public List<Article> getArticles() {

    LOG.info("Выведен список статей для данного пользователя");
    return articles;
  }
}
