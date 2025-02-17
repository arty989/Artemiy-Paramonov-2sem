package com.example.S2_H1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CategoryCreateDto", description = "DTO для данных категории")
public class CategoryDto {
  @Schema(description = "Название категории", example = "MTS")
  private String categoryName;
}
