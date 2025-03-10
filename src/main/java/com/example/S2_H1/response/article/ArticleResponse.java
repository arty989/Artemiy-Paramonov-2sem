package com.example.S2_H1.response.article;

import com.example.S2_H1.entity.Article;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "ArticleResponse", description = "DTO для возвращаемой статьи")
public class ArticleResponse {
  @Schema(description = "Возвращаемый id пользователя", example = "1")
  @NotNull
  private final Long articleId;

  @Schema(description = "Возвращаемый заголовок статьи", example = "MTS vs Yandex")
  @NotNull
  private final String articleTitle;

  @Schema(description = "Возвращаемый url статьи", example = "Http://mts.ru")
  @NotNull
  private final String articleUrl;

  @Schema(description = "Возвращаемое название категории", example = "МТС")
  @NotNull
  private final String categoryName;

  public ArticleResponse(Article article) {
    this.articleId = article.getArticleId();
    this.articleTitle = article.getArticleTitle();
    this.articleUrl = article.getArticleUrl();
    this.categoryName = article.getCategory().getCategoryName();
  }
}
