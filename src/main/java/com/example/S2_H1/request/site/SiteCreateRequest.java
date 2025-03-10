package com.example.S2_H1.request.site;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SiteCreateRequest {
  @Schema(name = "SiteCreateRequest", description = "DTO для создания сайта")
  @NotNull(message = "Site url has to be filled")
  private String siteUrl;
}
