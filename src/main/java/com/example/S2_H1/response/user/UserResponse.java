package com.example.S2_H1.response.user;

import com.example.S2_H1.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "UserResponse", description = "DTO для возвращаемого пользователя")
public class UserResponse {
  @Schema(description = "Возвращаемый id пользователя", example = "1")
  @NotNull
  private final Long userId;

  @Schema(description = "Возвращаемое имя пользователя", example = "Артемий")
  @NotNull
  private final String userName;

  @Schema(description = "Возвращаемый пароль пользователя", example = "1234")
  @NotNull
  private final String userPassword;

  @Schema(description = "Возвращаемый email пользователя", example = "mts@yandex.ru")
  @NotNull
  private final String userEmail;

  public UserResponse(User user) {
    this.userId = user.getUserId();
    this.userName = user.getUserName();
    this.userPassword = user.getUserPassword();
    this.userEmail = user.getUserEmail();
  }
}
