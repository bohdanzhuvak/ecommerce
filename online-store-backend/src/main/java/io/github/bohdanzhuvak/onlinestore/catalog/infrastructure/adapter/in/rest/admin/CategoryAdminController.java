package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
import io.github.bohdanzhuvak.onlinestore.catalog.application.services.WebCatalogOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.PagedResponse;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "categories-admin", description = "Category management operations for administrators")
@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryAdminController {
  private final WebCatalogOrchestratorService catalogService;

  public CategoryAdminController(WebCatalogOrchestratorService catalogService) {
    this.catalogService = catalogService;
  }

  @Operation(operationId = "getAllCategories", summary = "Get all categories", description = "Get paginated list of all categories (admin only)")
  @GetMapping
  public ResponseEntity<PagedResponse<CategoryResponse>> getAllCategories(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int perPage) {
    PageResult<Category> pageResult = catalogService.getAllCategoriesPaged(page, perPage);
    PagedResponse<CategoryResponse> response = PagedResponse.from(pageResult, CategoryResponse::from);
    return ResponseEntity.ok(response);
  }

  @Operation(operationId = "getCategoryById", summary = "Get category by ID", description = "Get a single category by its ID (admin only)")
  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable String id) {
    CategoryId categoryId = CategoryId.of(id);
    Optional<Category> category = catalogService.getCategory(categoryId);
    return category.map(c -> ResponseEntity.ok(CategoryResponse.from(c)))
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(operationId = "createCategory", summary = "Create new category", description = "Create a new product category (admin only)")
  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
    try {
      CreateCategoryUseCase.CreateCategoryCommand command = new CreateCategoryUseCase.CreateCategoryCommand(
          request.name(),
          request.description()
      );

      Category category = catalogService.createCategory(command);
      return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.from(category));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "updateCategory", summary = "Update category", description = "Update an existing category (admin only)")
  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String id, @RequestBody UpdateCategoryRequest request) {
    try {
      CategoryId categoryId = CategoryId.of(id);
      UpdateCategoryUseCase.UpdateCategoryCommand command = new UpdateCategoryUseCase.UpdateCategoryCommand(
          categoryId,
          request.name(),
          request.description()
      );

      Category category = catalogService.updateCategory(command);
      return ResponseEntity.ok(CategoryResponse.from(category));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "deleteCategory", summary = "Delete category", description = "Delete a category by its ID (admin only)")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
    try {
      CategoryId categoryId = CategoryId.of(id);
      catalogService.deleteCategory(categoryId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
