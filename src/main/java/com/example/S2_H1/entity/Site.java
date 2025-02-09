package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Site {
  private SiteId id;
  private String url;
  private UserId userId;
}
