package com.example.S2_H1.api;

import com.example.S2_H1.request.site.SiteCreateRequest;
import com.example.S2_H1.request.site.SiteIdRequest;
import com.example.S2_H1.response.site.SiteIdResponse;
import com.example.S2_H1.response.site.SiteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Site API", description = "Управление сайтами")
@RequestMapping("/api/sites")
public interface SiteApi {
  @Operation(
    summary = "Получить список доступных сайтов",
    description = "Возвращает список доступных сайтов, которые пользователь может использовать"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Список сайтов получен"),
  })
  @GetMapping
  ResponseEntity<List<SiteResponse>> getAllAvailableSites();

  @Operation(
    summary = "Получить список сайтов пользователя по ID",
    description = "Получает ID пользователя из заголовков запроса (headers) и возвращает список сайтов, которые выбрал пользователь",
    parameters = {
      @Parameter(
        name = "userId",
        in = ParameterIn.HEADER,
        required = true,
        description = "ID пользователя",
        example = "123"
      )
    }
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Список сайтов пользователя получен"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @GetMapping("/user")
  ResponseEntity<List<SiteResponse>> getUserSites(
    @RequestHeader("userId") Long userId
  );

  @Operation(
    summary = "Добавить сайт пользователю",
    description = "Получает ID пользователя из заголовков запроса (headers) и данные сайта, добавляет сайт пользователю",
    parameters = {
      @Parameter(
        name = "userId",
        in = ParameterIn.HEADER,
        required = true,
        description = "ID пользователя",
        example = "123"
      )
    }
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Сайт успешно добавлен пользователю"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
    @ApiResponse(responseCode = "404", description = "Сайт с такими данными не найден")
  })
  @PatchMapping("/user/add")
  ResponseEntity<Void> addSiteToUser(
    @RequestHeader("userId") Long userId,

    @RequestBody SiteIdRequest siteIdRequest
  );

  @Operation(
    summary = "Удалить сайт у пользователя",
    description = "Получает ID пользователя из заголовков запроса (headers) и сайта из body, удаляет сайт у пользователя",
    parameters = {
      @Parameter(
        name = "userId",
        in = ParameterIn.HEADER,
        required = true,
        description = "ID пользователя",
        example = "123"
      )
    }
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Сайт удалён у пользователя"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
    @ApiResponse(responseCode = "404", description = "Сайт с таким ID не найден")
  })
  @DeleteMapping("/user/delete")
  ResponseEntity<Void> deleteSiteFromUser(
    @RequestHeader("userId") Long userId,

    @RequestBody SiteIdRequest siteIdRequest
  );

  @Operation(
    summary = "Создать сайт",
    description = "Получает url сайта и создаёт его"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Сайт успешно создан"),
  })
  @PostMapping("/create")
  ResponseEntity<SiteIdResponse> addSite(@RequestBody SiteCreateRequest siteCreateRequest);

  @Operation(
    summary = "Удалить сайт по ID",
    description = "Получает ID сайта и удаляет его из репозитория"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Сайт успешно удалён"),
    @ApiResponse(responseCode = "404", description = "Сайт не найден")
  })
  @DeleteMapping("/delete/{siteId}")
  ResponseEntity<Void> deleteSite(
    @Parameter(description = "ID сайта", example = "1")
    @PathVariable Long siteId
  );
}
