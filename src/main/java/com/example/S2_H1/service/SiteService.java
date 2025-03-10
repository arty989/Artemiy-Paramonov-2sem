package com.example.S2_H1.service;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.repository.SiteRepository;
import com.example.S2_H1.repository.UserRepository;
import com.example.S2_H1.request.site.SiteCreateRequest;
import com.example.S2_H1.request.site.SiteIdRequest;
import com.example.S2_H1.response.site.SiteIdResponse;
import com.example.S2_H1.response.site.SiteResponse;
import com.example.S2_H1.service.exception.NoSuchSiteException;
import com.example.S2_H1.service.exception.NoSuchUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SiteService {

  private final SiteRepository siteRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<SiteResponse> getAllAvailableSites() {
    List<SiteResponse> responseSites = new ArrayList<>();
    for (Site site : siteRepository.findAll()) {
      responseSites.add(new SiteResponse(site));
    }
    return responseSites;
  }

  //At least once
  @Transactional(readOnly = true)
  @Retryable(retryFor = NoSuchUserException.class, maxAttempts = 5, backoff = @Backoff(delay = 10_000))
  public List<SiteResponse> getUserSites(Long userId) {
    log.info("Получение всех сайтов юзера с айди {}", userId);

    try {
      User user = getUserById(userId);
      log.info("Пользователь найден, получаем его сайты");

      List<SiteResponse> responseSites = new ArrayList<>();
      for (Site site : user.getSites()) {
        responseSites.add(new SiteResponse(site));
      }
      return responseSites;
    } catch (NoSuchUserException e) {
      log.warn("Пользователь не найден, повторяю попытку");

      throw e;
    }
  }

  @Transactional
  public void deleteSiteFromUser(Long userId, SiteIdRequest siteIdRequest) {
    log.info("Удаление сайта с айди {} у юзера с айди {}", siteIdRequest.getSiteId(), userId);

    User user = getUserById(userId);
    log.info("Пользователь найден");

    Site site = getSiteById(siteIdRequest.getSiteId());
    user.deleteSite(site);
    site.deleteUser(user);
    log.info("Операция завершена");

    siteRepository.save(site);
    userRepository.save(user);
  }

  @Transactional
  public void addSiteToUser(Long userId, SiteIdRequest siteIdRequest) {
    Long siteId = siteIdRequest.getSiteId();

    log.info("Добавление сайта с айди {} юзеру с айди {}", siteId, userId);

    User user = getUserById(userId);
    log.info("Пользователь с id {} найден", userId);

    Site site = getSiteById(siteId);
    log.info("Сайт с id {} найден", siteId);

    user.addSite(site);
    log.info("Сайт успешно добавлен пользователю");

    site.addUser(user);
    log.info("Пользователь успешно добавлен сайту");

    siteRepository.save(site);
    userRepository.save(user);
    log.info("Изменения сохранены в репозитории");
  }

  @Transactional
  public SiteIdResponse createSite(SiteCreateRequest siteCreateRequest) {
    log.info("Создание нового сайта");

    Site site = new Site(siteCreateRequest.getSiteUrl());
    log.info("Сайт создан");

    siteRepository.save(site);
    log.info("Сайт сохранён");

    return new SiteIdResponse(site.getSiteId());
  }

  @Transactional
  public void deleteSite(Long siteId) {
    log.info("Удаление сайта с id {}", siteId);

    if (siteRepository.existsById(siteId)) {
      siteRepository.deleteById(siteId);
      log.info("Сайт с id {} удалён из репозитория", siteId);
    } else {
      log.info("Сайт с id {} не был найден", siteId);
      throw new NoSuchSiteException("Сайт с id " + siteId + " не найден");
    }
  }

  @Transactional(readOnly = true)
  private User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NoSuchUserException("Пользователь с id " + userId + " не найден"));
  }

  @Transactional(readOnly = true)
  private Site getSiteById(Long siteId) {
    return siteRepository.findById(siteId).orElseThrow(() -> new NoSuchSiteException("Сайт с id " + siteId + " не найден"));
  }
}
