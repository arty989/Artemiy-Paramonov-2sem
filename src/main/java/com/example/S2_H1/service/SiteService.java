package com.example.S2_H1.service;

import com.example.S2_H1.dto.SiteDto;
import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.Sites;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.SiteRepository;
import com.example.S2_H1.repository.exception.UserNotFoundException;
import com.example.S2_H1.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class SiteService {

  private final SiteRepository siteRepository;

  public Map<SiteId, String> getAllAvailableSites() {
    Map<SiteId, String> sitesMap = new HashMap<>();

    for (Sites site : Sites.values()) {
      sitesMap.put(site.getId(), site.getUrl());
    }
    return sitesMap;
  }

  //At least once
  @Retryable(retryFor = NoSuchUserException.class, maxAttempts = 5, backoff = @Backoff(delay = 10_000))
  public List<Site> getUserSites(Long userId) {
    log.info("Получение всех сайтов юзера с айди {}", userId);
    try {
      return siteRepository.findAllSite(new UserId(userId));
    } catch (UserNotFoundException e) {
      log.warn("Пользователь не найден, повторяю попытку");
      throw new NoSuchUserException("Пользователь не найден", e);
    }
  }


  public void deleteSite(Long siteId, Long userId) {
    log.info("Удаление сайта с айди {} у юзера с айди {}", siteId, userId);
    siteRepository.deleteSiteById(new SiteId(siteId), new UserId(userId));
  }

  public void addSite(SiteDto siteDto, Long userId) {
    log.info("Добавление сайта с айди {} юзеру с айди {}", siteDto.getSiteId(), userId);
    SiteId siteId = new SiteId(siteDto.getSiteId());
    for (Sites enumSite : Sites.values()) {
      if (enumSite.getId().equals(siteId)) {
        Site site = Site.builder().id(siteId).userId(new UserId(userId)).url(enumSite.getUrl()).build();
        log.info("Сайт с айди {} найден", siteId.siteId());
        siteRepository.add(site);
      }
    }
    log.info("Добавление сайта с айди {} юзеру с айди {}", siteId, userId);
  }
}
