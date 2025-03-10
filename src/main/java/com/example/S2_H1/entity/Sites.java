package com.example.S2_H1.entity;

public enum Sites {
  SITE1(new SiteId(1L),"https://xakep.ru/"),
  SITE2(new SiteId(2L),"https://habr.com/ru/news/"),
  SITE3(new SiteId(3L),"https://timeweb.com/ru/community/"),
  SITE4(new SiteId(4L),"https://3dnews.ru/"),
  SITE5(new SiteId(5L),"https://www.ixbt.com/news/");

  private final String url;
  private final SiteId id;

  Sites(SiteId id, String url) {
    this.url = url;
    this.id = id;
  }

  public String getUrl() {
    return url;
  }
  public SiteId getId() {
    return id;
  }
}