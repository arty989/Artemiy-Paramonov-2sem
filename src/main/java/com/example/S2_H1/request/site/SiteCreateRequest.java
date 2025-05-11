package com.example.S2_H1.request.site;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "SiteCreateRequest", description = "DTO для создания сайта")
public class SiteCreateRequest {
  @Schema(description = "Url сайта", example = "https://www.mtsbank.ru/")
  @NotNull(message = "Site url has to be filled")
  private String siteUrl;
}
