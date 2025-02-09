package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Article;
import com.example.S2_H1.entity.ArticleId;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.UserId;

import java.util.Map;

public interface ArticleRepository {
  Map<Article, Category> getArticles(UserId userId, CategoryRepository categoryRepository);
}
