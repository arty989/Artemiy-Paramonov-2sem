package com.example.S2_H1.response.site;

import com.example.S2_H1.entity.Site;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "SiteResponse", description = "DTO для возвращаемого сайта")
public class SiteResponse {
  @Schema(description = "Возвращаемый id сайта", example = "1")
  @NotNull
  private final Long siteId;

  @Schema(description = "Возвращаемый url сайта", example = "http://mts.ru")
  @NotNull
  private final String siteUrl;

  public SiteResponse(Site site) {
    this.siteId = site.getSiteId();
    this.siteUrl = site.getSiteUrl();
  }
}
