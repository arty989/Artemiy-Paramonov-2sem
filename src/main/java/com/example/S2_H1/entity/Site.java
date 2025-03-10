package com.example.S2_H1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "sites")
public class Site {
  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "site_id", nullable = false)
  private Long siteId;

  @Getter
  @NotNull(message = "Site url has to be filled")
  @Column(name = "site_url", nullable = false)
  private String siteUrl;

  public Site(String url) {
    this.siteUrl = url;
  }

  @ManyToMany(mappedBy = "sites")
  private Set<User> users = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Site site)) {
      return false;
    }
    return siteId != null && siteId.equals(site.siteId);
  }

  @Override
  public int hashCode() {
    return siteId != null ? siteId.hashCode() : super.hashCode();
  }
}
