package com.example.S2_H1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "UserUpdateNameDto", description = "DTO для обновления имени пользователя по его ID")
public class UserUpdateNameDto {
  @Schema(description = "Новое имя пользователя", example = "Вадим")
  private String newUserName;
}
