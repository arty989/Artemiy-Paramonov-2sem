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

  public List<Site> getSites(Long userId) {
    return siteRepository.findAllSite(new UserId(userId));
  }

  public void deleteSite(Long siteId, Long userId) {
    siteRepository.deleteSiteById(new SiteId(siteId), new UserId(userId));
  }

  public void addSite(Long siteId, Long userId) {
    siteRepository.add(new SiteId(siteId), new UserId(userId));
  }
}
