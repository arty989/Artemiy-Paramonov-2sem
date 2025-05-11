package com.example.S2_H1.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "UserIdResponse", description = "DTO для возвращаемого id пользователя")
public class UserIdResponse {
  @Schema(description = "Возвращаемый id пользователя", example = "1")
  @NotNull
  private Long userId;
}
