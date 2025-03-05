package com.example.S2_H1.api;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Article API", description = "Управление статьями")
@RequestMapping("/api/articles")
public interface ArticleApi {

  @Operation(
    summary = "Получить список статей пользователя по ID",
    description = "Получает ID пользователя и возвращает список статей, которые ml посчитала наиболее подходящими для него"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Статьи получены"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @GetMapping("/{userId}")
  CompletableFuture<ResponseEntity<Map<Article, Category>>> getArticlesForUser(
    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );
}
