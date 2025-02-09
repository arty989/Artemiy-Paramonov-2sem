package com.example.S2_H1.repositories;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.UserId;

import java.util.List;

public interface SiteRepository {
  List<Site> findAllSite(UserId userId);
  void deleteSiteById(SiteId id, UserId userId);
  void deleteByUserId(UserId userId);
  void add(SiteId siteId, UserId userId);
}
