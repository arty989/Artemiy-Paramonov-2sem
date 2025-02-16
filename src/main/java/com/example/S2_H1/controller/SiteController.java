package com.example.S2_H1.controller;

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
@RequestMapping("/api/sites")
public class SiteController {
  private final SiteService siteService;

  @GetMapping
  public ResponseEntity<Map<SiteId, String>> getAllAvailableSites() {
    return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAvailableSites());
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Site>> getUserSites(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(siteService.getUserSites(userId));
  }

  @PutMapping("/add/{userId}/{siteId}")
  public ResponseEntity<Void> addSiteToUser(@PathVariable Long userId, @PathVariable Long siteId) {
    siteService.addSite(siteId, userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/delete/{userId}/{siteId}")
  public ResponseEntity<Void> deleteSiteFromUser(@PathVariable Long userId, @PathVariable Long siteId) {
    siteService.deleteSite(siteId, userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
