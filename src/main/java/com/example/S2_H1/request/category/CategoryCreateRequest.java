package com.example.S2_H1.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CategoryCreateRequest", description = "DTO для создания категории")
public class CategoryCreateRequest {
  @Schema(description = "Название категории", example = "MTS")
  private String categoryName;
}
