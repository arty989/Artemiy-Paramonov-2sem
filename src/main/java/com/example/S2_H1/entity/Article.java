package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {
  String name;
  ArticleId id;
  String url;
  CategoryId categoryId;
}
