package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import com.example.S2_H1.entity.UserId;
import com.example.S2_H1.repository.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@AllArgsConstructor
public class InMemorySiteRepository implements SiteRepository {

  private final List<Site> sites = new ArrayList<>();

  private final RestTemplate restTemplate;
  private final WebClient webClient;

  @Override
  public List<Site> findAllSite(UserId userId) {
    List<Site> answerSites = new ArrayList<>();
    UUID response = restTemplate.getForObject("http://localhost:8080/admin/actuator/uuid", UUID.class);
    log.info("Рандомный UUID: {}", response);
    for (Site site : sites) {
      if (site.getUserId().equals(userId)) {
        answerSites.add(site);
      }
    }
    log.info("Репозиторий вернул список всех сайтов пользователя с айди {}", userId.id());
    if (!answerSites.isEmpty()) {
      return answerSites;
    }
    throw new UserNotFoundException("Пользователь с id: " + userId + " не найден.");
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
    UUID response = webClient.get().uri("http://localhost:8080/admin/actuator/uuid")
        .retrieve().bodyToMono(UUID.class).block();
    log.info("Рандомный UUID: {}", response);
    sites.add(site);
  }
}
