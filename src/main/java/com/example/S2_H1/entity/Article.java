package com.example.S2_H1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "articles")
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id")
  private Long articleId;

  @Getter
  @NotNull(message = "Article name has to be filled")
  @Column(name = "title", nullable = false)
  private String articleTitle;

  @Getter
  @NotNull(message = "Article url has to be filled")
  @Column(name = "url", nullable = false)
  private String articleUrl;

  public Article(String title, String url) {
    this.articleTitle = title;
    this.articleUrl = url;
  }

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id",  nullable = false)
  @NotNull(message = "Article category has to be filled")
  private Category category;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Article article)) {
      return false;
    }
    return articleId != null && articleId.equals(article.articleId);
  }

  @Override
  public int hashCode() {
    return articleId != null ? articleId.hashCode() : super.hashCode();
  }
}
