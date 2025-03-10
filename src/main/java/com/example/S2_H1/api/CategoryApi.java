package com.example.S2_H1.api;

import com.example.S2_H1.request.category.CategoryCreateRequest;
import com.example.S2_H1.entity.Category;
import com.example.S2_H1.request.category.CategoryUpdateDataRequest;
import com.example.S2_H1.response.category.CategoryIdResponse;
import com.example.S2_H1.response.category.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Category API", description = "Управление категориями")
@RequestMapping("/api/categories")
public interface CategoryApi {
  @Operation(
    summary = "Получить список категорий пользователя по ID",
    description = "Получает ID пользователя и возвращает список категорий, которые он себе добавил"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Список категорий пользователя получен"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @GetMapping("/user/{userId}")
  ResponseEntity<List<CategoryResponse>> getUserCategories(
    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Получить категорию по ID",
    description = "Получает ID категории и возвращает её"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Категория получена"),
    @ApiResponse(responseCode = "404", description = "Категория с таким ID не найдена")
  })
  @GetMapping("/{categoryId}")
  ResponseEntity<CategoryResponse> getCategoryById(
    @Parameter(description = "ID категории", example = "1")
    @PathVariable Long categoryId
  );

  @Operation(
    summary = "Удалить категорию по ID",
    description = "Получает ID категории и удаляет её"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Категория успешно удалена"),
    @ApiResponse(responseCode = "404", description = "Категория с таким ID не найдена")
  })
  @DeleteMapping("/delete/{categoryId}")
  ResponseEntity<Void> deleteCategoryById(
    @Parameter(description = "ID категории", example = "1")
    @PathVariable Long categoryId
  );

  @Operation(
    summary = "Удалить все категории пользователя по его ID",
    description = "Получает ID пользователя и удаляет все его категории"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Категории пользователя успешно удалены"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @DeleteMapping("/delete/all/user/{userId}")
  ResponseEntity<Void> deleteUserCategories(
    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Создать новую категорию пользователю",
    description = "Получает ID пользователя и данные категории, создаёт пользователю новую категорию"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Категория успешно создана"),
    @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
  })
  @PostMapping("/create/user/{userId}")
  ResponseEntity<CategoryIdResponse> createCategoryForUser(
    @RequestBody CategoryCreateRequest categoryDto,

    @Parameter(description = "ID пользователя", example = "1")
    @PathVariable Long userId
  );

  @Operation(
    summary = "Обновить данные категории",
    description = "Получает ID категории и новые данные, обновляет данные категории"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Данные категории успешно обновлены"),
    @ApiResponse(responseCode = "404", description = "Категория с таким ID не найдена")
  })
  @PutMapping("/update/{categoryId}")
  ResponseEntity<CategoryResponse> updateCategoryData(
    @RequestBody CategoryUpdateDataRequest categoryUpdateDataRequest,

    @Parameter(description = "ID категории", example = "1")
    @PathVariable Long categoryId
  );
}
