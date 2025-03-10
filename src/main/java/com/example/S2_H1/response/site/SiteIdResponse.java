package com.example.S2_H1.response.site;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "SiteIdResponse", description = "DTO для возвращаемого id сайта")
public class SiteIdResponse {
  @Schema(description = "Возвращаемый id сайта", example = "1")
  @NotNull
  private Long userId;
}
