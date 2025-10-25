package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.catalog.application.services.WebCatalogOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer/categories")
public class CategoryCustomerController {
  private final WebCatalogOrchestratorService catalogService;

  public CategoryCustomerController(WebCatalogOrchestratorService catalogService) {
    this.catalogService = catalogService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllCategories() {
    List<Category> categories = catalogService.getAllCategories();
    return ResponseEntity.ok(CategoryResponse.from(categories));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable String id) {
    CategoryId categoryId = CategoryId.of(id);
    Optional<Category> category = catalogService.getCategory(categoryId);
    return category.map(c -> ResponseEntity.ok(CategoryResponse.from(c)))
        .orElse(ResponseEntity.notFound().build());
  }
}
