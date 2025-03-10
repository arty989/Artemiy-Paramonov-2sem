package com.example.S2_H1.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "UserUpdateNameDto", description = "DTO для обновления имени пользователя по его ID")
public class UserUpdateNameDto {
  @Schema(description = "Новое имя пользователя", example = "Вадим")
  @NotNull(message = "New user name has to be filled")
  private String newUserName;
}
