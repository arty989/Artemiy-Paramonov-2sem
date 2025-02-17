package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {
  private String name;
  private ArticleId id;
  private String url;
  private CategoryId categoryId;
}
