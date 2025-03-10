package com.example.S2_H1.service;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.repository.ArticleRepository;
import com.example.S2_H1.repository.UserRepository;
import com.example.S2_H1.response.article.ArticleResponse;
import com.example.S2_H1.service.exception.NoSuchArticleException;
import com.example.S2_H1.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/* По-сути это сервис для обработки и получения статей пользователя,
  но в связи с отсутствием парсеров и мл, просто "болванка" этого сервиса.
  Частично показана реализация, но не затронута связь OneToMany между Category и Article,
  и особо не задействован ArticleRepository, но заложенный в них функционал в целом позволяет
  с минимальными изменениями интегрировать мл и парсеры.
*/
@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  @Async
  @Transactional
  public CompletableFuture<List<ArticleResponse>> getArticles(Long userId) {
    log.info("Получение текущих статей");

    List<Category> userCategories = getUserCategories(userId);
    log.info("Получены категории пользователя");

    // В проекте должна быть логика получения статей, для пользователя спаршенных из его сайтов, и затем распределение их по категориям
    List<ArticleResponse> articlesForCategories = new ArrayList<>();

    //Рандомный вывод, т.к. в дз не реализованы парсеры, то выводим простую заготовку, добавленную в миграциях
    log.info("Сопоставление статей категориям");
    for (Category category : userCategories) {
      articlesForCategories.add(new ArticleResponse(getArticleById(1L)));
    }

    log.info("Вернули юзеру список статей и категорий, к которым они относятся");
    return CompletableFuture.completedFuture(articlesForCategories);
  }

  @Transactional(readOnly = true)
  private List<Category> getUserCategories(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchUserException("Пользователь с id " + userId + " не найден"));
    List<Category> categories = user.getCategories();
    return categories;
  }

  @Transactional(readOnly = true)
  private Article getArticleById(Long articleId) {
    return articleRepository.findById(articleId).orElseThrow(() -> new NoSuchArticleException("Статья с id " + articleId + " не найден"));
  }
}
