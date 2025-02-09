package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
  private CategoryId categoryId;
  private String name;
  private UserId userId;
}
