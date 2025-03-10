package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Site;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class SiteRepositoryTest {

  @Autowired
  SiteRepository siteRepository;

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @BeforeEach
  void setUp() {
    siteRepository.deleteAll();
  }

  @Test
  void testSaveSite() {
    Site site = new Site("http://mts.ru");
    siteRepository.save(site);

    assertThat(site.getSiteId()).isNotNull();
    assertThat(site.getSiteUrl()).isEqualTo("http://mts.ru");
  }

  @Test
  void testFindById() {
    Site site = new Site("http://mts.ru");
    siteRepository.save(site);

    Optional<Site> foundSite = siteRepository.findById(site.getSiteId());

    assertThat(foundSite).isPresent();
    assertThat(foundSite.get().getSiteUrl()).isEqualTo("http://mts.ru");
  }

  @Test
  void testFindAll() {
    Site site1 = new Site("http://mts.ru");

    Site site2 = new Site("http://yandex.ru");

    siteRepository.save(site1);
    siteRepository.save(site2);

    List<Site> sites = siteRepository.findAll();

    assertThat(sites.size()).isEqualTo(2);
  }

  @Test
  void testDeleteById() {
    Site site = new Site("http://mts.ru");

    siteRepository.save(site);

    siteRepository.deleteById(site.getSiteId());

    Optional<Site> deletedSite = siteRepository.findById(site.getSiteId());
    assertThat(deletedSite).isEmpty();
  }

  @Test
  void testDeleteAll() {
    Site site1 = new Site("http://mts.ru");

    Site site2 = new Site("http://yandex.ru");

    siteRepository.save(site1);
    siteRepository.save(site2);
    siteRepository.deleteAll();

    Optional<Site> deletedSite1 = siteRepository.findById(site1.getSiteId());
    Optional<Site> deletedSite2 = siteRepository.findById(site2.getSiteId());
    assertThat(deletedSite1).isEmpty();
    assertThat(deletedSite2).isEmpty();
  }

  @Test
  void testDeleteByEntity() {
    Site site = new Site("http://mts.ru");

    siteRepository.save(site);

    siteRepository.delete(site);

    Optional<Site> deletedSite = siteRepository.findById(site.getSiteId());
    assertThat(deletedSite).isEmpty();
  }

  @Test
  void testExistById() {
    Site site = new Site("http://yandex.ru");

    siteRepository.save(site);

    assertThat(siteRepository.existsById(site.getSiteId())).isEqualTo(true);
  }
}