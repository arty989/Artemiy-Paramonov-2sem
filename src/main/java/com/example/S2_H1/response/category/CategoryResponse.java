package com.example.S2_H1.response.category;

import com.example.S2_H1.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "CategoryResponse", description = "DTO для возвращаемой категории")
public class CategoryResponse {
  @Schema(description = "Возвращаемый id категории", example = "1")
  @NotNull
  private final Long categoryID;

  @Schema(description = "Возвращаемое название категории", example = "МТС")
  @NotNull
  private final String categoryName;

  @Schema(description = "Возвращаемый id владельца категории", example = "1")
  @NotNull
  private final Long userId;

  public CategoryResponse(Category category) {
    this.categoryID = category.getCategoryId();
    this.categoryName = category.getCategoryName();
    this.userId = category.getUser().getUserId();
  }
}
