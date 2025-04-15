package com.example.S2_H1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long categoryId;

  @Setter
  @NotNull(message = "Category name has to be filled")
  @Column(name = "category_name", nullable = false)
  private String categoryName;

  public Category(String name, User user) {
    this.categoryName = name;
    this.user = user;
  }

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @NotNull(message = "Category user has to be filled")
  private User user;

  @OneToMany(mappedBy = "category", cascade = PERSIST)
  private final List<Article> articles = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Category category)) {
      return false;
    }
    return categoryId != null && categoryId.equals(category.categoryId);
  }

  @Override
  public int hashCode() {
    return categoryId != null ? categoryId.hashCode() : super.hashCode();
  }
}
