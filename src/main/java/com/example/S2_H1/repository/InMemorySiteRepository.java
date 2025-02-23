package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class InMemorySiteRepository implements SiteRepository {

  private final List<Site> sites = new ArrayList<>();

  @Override
  public List<Site> findAllSite(UserId userId) {
    List<Site> answerSites = new ArrayList<>();
    for (Site site : sites) {
      if (site.getUserId().equals(userId)) {
        answerSites.add(site);
      }
    }
    log.info("Репозиторий вернул список всех сайтов пользователя с айди {}", userId.id());
    return answerSites;
  }

  @Override
  public void deleteSiteById(SiteId siteId, UserId userId) {
    for (Site site : sites) {
      if (site.getUserId().equals(userId) && site.getId().equals(siteId)) {
        sites.remove(site);
        log.info("Сайт с айди {} успешно удалён для юзера с айди {} из репозитория", siteId.siteId(), userId.id());
        break;
      }
    }
  }

  @Override
  public void deleteByUserId(UserId userId) {
    for (Site site : sites) {
      if (site.getUserId().equals(userId)) {
        sites.remove(site);
        log.info("Сайт с айди {} успешно удалён для юзера с айди {} из репозитория", site.getId().siteId(), userId.id());
        break;
      }
    }
  }

  @Override
  public void add(Site site) {
    sites.add(site);
  }
}
