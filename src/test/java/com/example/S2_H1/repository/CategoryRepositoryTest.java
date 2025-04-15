package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Category;
import com.example.S2_H1.entity.User;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class CategoryRepositoryTest {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  UserRepository userRepository;

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @BeforeEach
  void setUp() {
    categoryRepository.deleteAll();
  }

  @Test
  void testSaveCategory() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category = new Category("MTS", user);
    categoryRepository.save(category);

    assertThat(category.getCategoryId()).isNotNull();
    assertThat(category.getCategoryName()).isEqualTo("MTS");
  }

  @Test
  void testFindById() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category = new Category("MTS", user);

    categoryRepository.save(category);

    Optional<Category> foundCategory = categoryRepository.findById(category.getCategoryId());

    assertThat(foundCategory).isPresent();
    assertThat(foundCategory.get().getCategoryName()).isEqualTo("MTS");
  }

  @Test
  void testDeleteById() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category = new Category("MTS", user);

    categoryRepository.save(category);

    categoryRepository.deleteById(category.getCategoryId());

    Optional<Category> deletedCategory = categoryRepository.findById(category.getCategoryId());
    assertThat(deletedCategory).isEmpty();
  }

  @Test
  void testUpdateCategory() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category = new Category("MTS", user);

    categoryRepository.save(category);

    category.setCategoryName("Yandex");

    categoryRepository.save(category);

    assertThat(category.getCategoryName()).isEqualTo("Yandex");
  }

  @Test
  void testDeleteByUserUserId() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category1 = new Category("MTS", user);
    Category category2 = new Category("Yandex", user);

    categoryRepository.save(category1);
    categoryRepository.save(category2);

    categoryRepository.deleteByUserUserId(user.getUserId());

    Optional<Category> deletedCategory1 = categoryRepository.findById(category1.getCategoryId());
    Optional<Category> deletedCategory2 = categoryRepository.findById(category2.getCategoryId());
    assertThat(deletedCategory1).isEmpty();
    assertThat(deletedCategory2).isEmpty();
  }

  @Test
  void testDeleteByEntity() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category = new Category("MTS", user);

    categoryRepository.save(category);

    categoryRepository.delete(category);

    Optional<Category> deletedUser = categoryRepository.findById(category.getCategoryId());
    assertThat(deletedUser).isEmpty();
  }

  @Test
  void testExistById() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    Category category = new Category("MTS", user);

    categoryRepository.save(category);

    assertThat(categoryRepository.existsById(category.getCategoryId())).isEqualTo(true);
  }
}