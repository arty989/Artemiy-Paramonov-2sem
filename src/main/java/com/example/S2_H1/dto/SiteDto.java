package com.example.S2_H1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SiteDto", description = "DTO для данных сайта")
public class SiteDto {
  @Schema(description = "ID сайта", example = "1")
  private Long siteId;
}
