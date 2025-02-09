package com.example.S2_H1.controllers;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.services.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sites")
public class SiteController {
  private final SiteService siteService;

  @GetMapping("/{userId}")
  public List<Site> getUserSites(@PathVariable Long userId) {
    return siteService.getSites(userId);
  }

  @PutMapping("/add/{userId}/{siteId}")
  public void addSiteToUser(@PathVariable Long userId, @PathVariable Long siteId) {
    siteService.addSite(siteId, userId);
  }

  @DeleteMapping("/delete/{userId}/{siteId}")
  public void deleteSiteFromUser(@PathVariable Long userId, @PathVariable Long siteId) {
    siteService.deleteSite(siteId, userId);
  }
}
