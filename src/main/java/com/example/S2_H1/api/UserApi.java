package com.example.S2_H1.api;

import com.example.S2_H1.dto.UserDto;
import com.example.S2_H1.dto.UserUpdateNameDto;
import com.example.S2_H1.entity.User;
import com.example.S2_H1.entity.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "Управление пользователями")
@RequestMapping("/api/users")
public interface UserApi {

  @Operation(
    summary = "Зарегистрировать нового пользователя",
    description = "Получает от пользователя Имя и Пароль в json формате и добавляет пользователя в репозиторий, возвращая его уникальный ID"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Пользователь создан"),
  })
  @PostMapping("/register")
  ResponseEntity<UserId> registerUser(@RequestBody UserDto userDto);

  @Operation(
    summary = "Удалить пользователя по ID",
    description = "Получает ID пользователя и удаляет его из репозитория"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён"),
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  })
  @DeleteMapping("/delete/{userId}")
  ResponseEntity<Void> deleteUser(
    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Изменить имя пользователя по ID",
    description = "Получает ID пользователя и новое имя. Меняет имя пользователя в репозитории"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Имя пользователя успешно изменено"),
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  })
  @PatchMapping("/rename/{userId}")
  ResponseEntity<User> updateUserName(
    @RequestBody UserUpdateNameDto userUpdateNameDto,

    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Обновить данные пользователя по ID",
    description = "Получает ID пользователя и новые данные. Меняет данные пользователя в репозитории"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Данные пользователя успешно изменены"),
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  })
  @PutMapping("/rename/{userId}")
  ResponseEntity<User> updateUserData(
    @RequestBody UserDto userDto,

    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );
}
