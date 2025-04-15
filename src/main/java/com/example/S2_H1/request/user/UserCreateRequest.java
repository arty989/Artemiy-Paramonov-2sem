package com.example.S2_H1.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "UserCreateRequest", description = "DTO для создания пользователя")
public class UserCreateRequest {

  @NotNull(message = "User name has to be filled")
  @Schema(description = "Имя пользователя", example = "Artemiy")
  private String userName;

  @NotNull(message = "User password has to be filled")
  @Schema(description = "Пароль пользователя", example = "12345678")
  private String userPassword;

  @NotNull(message = "User email has to be filled")
  @Schema(description = "Почта пользователя", example = "mts@yandex.ru")
  private String userEmail;
}
