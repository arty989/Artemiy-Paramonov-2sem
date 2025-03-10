package com.example.S2_H1.controller;

import com.example.S2_H1.api.SiteApi;
import com.example.S2_H1.request.site.SiteCreateRequest;
import com.example.S2_H1.request.site.SiteIdRequest;
import com.example.S2_H1.entity.Site;
import com.example.S2_H1.response.site.SiteIdResponse;
import com.example.S2_H1.response.site.SiteResponse;
import com.example.S2_H1.service.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SiteController implements SiteApi {
  private final SiteService siteService;

  @Override
  public ResponseEntity<List<SiteResponse>> getAllAvailableSites() {
    return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAvailableSites());
  }

  @Override
  public ResponseEntity<List<SiteResponse>> getUserSites(Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(siteService.getUserSites(userId));
  }

  @Override
  public ResponseEntity<Void> addSiteToUser(Long userId, SiteIdRequest siteIdRequest) {
    siteService.addSiteToUser( userId, siteIdRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<Void> deleteSiteFromUser(Long userId, SiteIdRequest siteIdRequest) {
    siteService.deleteSiteFromUser(userId, siteIdRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<SiteIdResponse> addSite(SiteCreateRequest siteCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(siteService.createSite(siteCreateRequest));
  }

  @Override
  public ResponseEntity<Void> deleteSite(Long siteId) {
    siteService.deleteSite(siteId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
