package com.example.S2_H1.services;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repositories.SiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SiteService {
  private final SiteRepository siteRepository;

  public List<Site> getSites(UserId userId) {
    return siteRepository.findAllSite(userId);
  }

  public void deleteSite(SiteId siteId, UserId userId) {
    siteRepository.deleteSiteById(siteId, userId);
  }

  public void addSite(SiteId siteId, UserId userId) {
    siteRepository.add(siteId, userId);
  }
}
