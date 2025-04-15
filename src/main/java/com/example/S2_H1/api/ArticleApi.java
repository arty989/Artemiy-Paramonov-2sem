package com.example.S2_H1.api;

import com.example.S2_H1.response.article.ArticleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Article API", description = "Управление статьями")
@RequestMapping("/api/articles")
public interface ArticleApi {

  @Operation(
    summary = "Получить список статей пользователя по ID",
    description = "Получает ID пользователя из заголовков запроса (headers) и возвращает список статей, которые ML-модель посчитала наиболее подходящими.",
    parameters = {
      @Parameter(
        name = "userId",
        in = ParameterIn.HEADER,
        required = true,
        description = "ID пользователя",
        example = "123"
      )
    }
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Статьи получены"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @GetMapping
  CompletableFuture<ResponseEntity<List<ArticleResponse>>> getArticlesForUser(
    @RequestHeader("userId") Long userId
  );
}
