package com.example.S2_H1.request.site;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "SiteIdRequest", description = "DTO для получения id сайта")
public class SiteIdRequest {
  @Schema(description = "ID сайта", example = "1")
  @NotNull(message = "Site id has to be filled")
  private Long siteId;
}
