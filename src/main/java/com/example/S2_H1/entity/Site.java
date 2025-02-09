package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Site {
  SiteId id;
  String url;
  UserId userId;
}
