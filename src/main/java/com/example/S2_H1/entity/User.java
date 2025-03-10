package com.example.S2_H1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
public class User {
  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Getter
  @Setter
  @NotNull(message = "User name has to be filled")
  @Column(name = "user_name", nullable = false)
  private String userName;

  @Getter
  @Setter
  @NotNull(message = "User password has to be filled")
  @Column(name = "user_password", nullable = false)
  private String userPassword;

  @Getter
  @Setter
  @Email(message = "Email must be correct")
  @NotNull(message = "Email has to be filled")
  @Column(name = "user_email", nullable = false)
  private String userEmail;

  public User(String name, String password, String email) {
    this.userName = name;
    this.userPassword = password;
    this.userEmail = email;
  }

  @OneToMany(mappedBy = "user", cascade = PERSIST)
  private final Set<Category> categories = new HashSet<>();

  public List<Category> getCategories() {
    return new ArrayList<>(categories);
  }

  public void addCategory(Category category) {
    categories.add(category);
  }

  @ManyToMany(cascade = PERSIST)
  @JoinTable(
    name = "user_site",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "site_id")
  )
  private final Set<Site> sites = new HashSet<>();

  public List<Site> getSites() {
    return new ArrayList<>(sites);
  }

  public void deleteSite(Long siteId) {
    sites.removeIf(site -> Objects.equals(site.getSiteId(), siteId));
  }

  public void addSite(Site site) {
    sites.add(site);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User user)) {
      return false;
    }
    return userId != null && userId.equals(user.userId);
  }

  @Override
  public int hashCode() {
    return userId != null ? userId.hashCode() : super.hashCode();
  }
}
