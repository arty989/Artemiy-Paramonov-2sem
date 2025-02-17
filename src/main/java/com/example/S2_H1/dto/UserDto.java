package com.example.S2_H1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "UserDto", description = "DTO для данных пользователя")
public class UserDto {

  @Schema(description = "Имя пользователя", example = "Artemiy")
  private String userName;

  @Schema(description = "Пароль пользователя", example = "12345678")
  private String userPassword;
}
