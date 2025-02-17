package com.example.S2_H1.controller;

import com.example.S2_H1.api.SiteApi;
import com.example.S2_H1.dto.SiteDto;
import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.service.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class SiteController implements SiteApi {
  private final SiteService siteService;

  @Override
  public ResponseEntity<Map<SiteId, String>> getAllAvailableSites() {
    return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAvailableSites());
  }

  @Override
  public ResponseEntity<List<Site>> getUserSites(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(siteService.getUserSites(userId));
  }

  @Override
  public ResponseEntity<Void> addSiteToUser(SiteDto siteDto, Long userId) {
    siteService.addSite(siteDto, userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/delete/{userId}/{siteId}")
  public ResponseEntity<Void> deleteSiteFromUser(@PathVariable Long userId, @PathVariable Long siteId) {
    siteService.deleteSite(siteId, userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
