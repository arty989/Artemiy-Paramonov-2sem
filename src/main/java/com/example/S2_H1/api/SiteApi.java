package com.example.S2_H1.api;

import com.example.S2_H1.dto.SiteDto;
import com.example.S2_H1.entity.Site;
import com.example.S2_H1.entity.SiteId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
  ResponseEntity<Map<SiteId, String>> getAllAvailableSites();

  @Operation(
    summary = "Получить список сайтов пользователя по ID",
    description = "Получает ID пользователя и возвращает список сайтов, которые выбрал пользователь"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Список сайтов пользователя получен"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @GetMapping("/user/{userId}")
  ResponseEntity<List<Site>> getUserSites(
    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Добавить сайт пользователю",
    description = "Получает ID пользователя и данные сайта, добавляет сайт пользователю"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Сайт успешно добавлен пользователю"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
    @ApiResponse(responseCode = "404", description = "Сайт с такими данными не найден")
  })
  @PatchMapping("/add/{userId}")
  ResponseEntity<Void> addSiteToUser(
    @RequestBody SiteDto siteDto,

    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Удалить сайт у пользователя",
    description = "Получает ID пользователя и сайта, удаляет сайт у пользователя"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Сайт удалён у пользователя"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
    @ApiResponse(responseCode = "404", description = "Сайт с таким ID не найден")
  })
  @DeleteMapping("/delete/{userId}/{siteId}")
  ResponseEntity<Void> deleteSiteFromUser(
    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId,

    @Parameter(description = "ID сайта", example = "1")
    @PathVariable Long siteId
  );
}
