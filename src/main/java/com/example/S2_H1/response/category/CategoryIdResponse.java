package com.example.S2_H1.response.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "CategoryIdResponse", description = "DTO для возвращаемого id категории")
public class CategoryIdResponse {
  @Schema(description = "Возвращаемый id категории", example = "1")
  @NotNull
  private Long categoryId;
}
