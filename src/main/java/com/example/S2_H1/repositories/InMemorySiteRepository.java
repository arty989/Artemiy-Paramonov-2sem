package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.Sites;
import com.example.S2_H1.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    return answerSites;
  }

  @Override
  public void deleteSiteById(SiteId id, UserId userId) {
    for (Site site : sites) {
      if (site.getUserId().equals(userId) && site.getId().equals(id)) {
        sites.remove(site);
        break;
      }
    }
  }

  @Override
  public void deleteByUserId(UserId userId) {
    for (Site site : sites) {
      if (site.getUserId().equals(userId)) {
        sites.remove(site);
        break;
      }
    }
  }

  @Override
  public void add(SiteId siteId, UserId userId) {
    for (Sites enumSite : Sites.values()) {
      if (enumSite.getId() == siteId) {
        Site site = Site.builder().id(siteId).userId(userId).url(enumSite.getUrl()).build();
        sites.add(site);
      }
    }
  }
}
