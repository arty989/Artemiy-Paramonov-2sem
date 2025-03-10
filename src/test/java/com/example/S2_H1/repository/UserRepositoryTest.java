package com.example.S2_H1.repository;

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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void testSaveUser() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");
    userRepository.save(user);

    assertThat(user.getUserId()).isNotNull();
    assertThat(user.getUserName()).isEqualTo("Артемий");
  }

  @Test
  void testFindById() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");
    userRepository.save(user);

    Optional<User> foundUser = userRepository.findById(user.getUserId());

    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getUserName()).isEqualTo("Артемий");
  }

  @Test
  void testFindAll() {
    User user1 = new User("Артемий", "12345678", "mts@yandex.ru");

    User user2 = new User("Вадим", "87654321", "yandex@mts.ru");

    userRepository.save(user1);
    userRepository.save(user2);

    List<User> users = userRepository.findAll();

    assertThat(users.size()).isEqualTo(2);
  }

  @Test
  void testDeleteById() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    userRepository.deleteById(user.getUserId());

    Optional<User> deletedUser = userRepository.findById(user.getUserId());
    assertThat(deletedUser).isEmpty();
  }

  @Test
  void testUpdateUser() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");
    userRepository.save(user);

    user.setUserName("Иван");

    userRepository.save(user);

    assertThat(user.getUserName()).isEqualTo("Иван");
  }

  @Test
  void testDeleteAll() {
    User user1 = new User("Артемий", "12345678", "mts@yandex.ru");

    User user2 = new User("Вадим", "87654321", "yandex@mts.ru");

    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.deleteAll();

    Optional<User> deletedUser1 = userRepository.findById(user1.getUserId());
    Optional<User> deletedUser2 = userRepository.findById(user2.getUserId());
    assertThat(deletedUser1).isEmpty();
    assertThat(deletedUser2).isEmpty();
  }

  @Test
  void testDeleteByEntity() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    userRepository.delete(user);

    Optional<User> deletedUser = userRepository.findById(user.getUserId());
    assertThat(deletedUser).isEmpty();
  }

  @Test
  void testExistById() {
    User user = new User("Артемий", "12345678", "mts@yandex.ru");

    userRepository.save(user);

    assertThat(userRepository.existsById(user.getUserId())).isEqualTo(true);
  }
}