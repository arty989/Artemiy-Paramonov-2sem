package com.example.S2_H1.services;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.Sites;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.SiteRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SiteService {
  private static final Logger LOG = LoggerFactory.getLogger(SiteService.class);

  private final SiteRepository siteRepository;

  public Map<SiteId, String> getAllAvailableSites() {
    Map<SiteId, String> sitesMap = new HashMap<>();

    for (Sites site : Sites.values()) {
      sitesMap.put(site.getId(), site.getUrl());
    }
    return sitesMap;
  }

  public List<Site> getUserSites(Long userId) {
    LOG.info("Получение всех сайтов юзера с айди {}", userId);
    return siteRepository.findAllSite(new UserId(userId));
  }

  public void deleteSite(Long siteId, Long userId) {
    LOG.info("Удаление сайта с айди {} у юзера с айди {}", siteId, userId);
    siteRepository.deleteSiteById(new SiteId(siteId), new UserId(userId));
  }

  public void addSite(Long parsSiteId, Long userId) {
    LOG.info("Добавление сайта с айди {} юзеру с айди {}", parsSiteId, userId);
    SiteId siteId = new SiteId(parsSiteId);
    for (Sites enumSite : Sites.values()) {
      if (enumSite.getId() == siteId) {
        Site site = Site.builder().id(siteId).userId(new UserId(userId)).url(enumSite.getUrl()).build();
        LOG.info("Сайт с айди {} найден", siteId.siteId());
        siteRepository.add(site);
      }
    }
    LOG.info("Добавление сайта с айди {} юзеру с айди {}", siteId, userId);
  }
}
