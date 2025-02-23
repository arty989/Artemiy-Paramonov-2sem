package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.ArticleId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class InMemoryArticleRepository implements ArticleRepository {

  //Т.к. реализация без парсера и мл, то статья здесь всегда одна, придуманная мной.
  private final AtomicLong nextArticleId = new AtomicLong(0);
  private final List<Article> articles = List.of(Article.builder().id(generateId()).name("МТС").build(), Article.builder().id(generateId()).name("МФТИ").build());

  private ArticleId generateId() {
    return new ArticleId(nextArticleId.incrementAndGet());
  }

  @Override
  public List<Article> getArticles() {

    log.info("Репозиторий вернул список статей");
    return articles;
  }
}
