package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
import io.github.bohdanzhuvak.onlinestore.catalog.application.services.WebCatalogOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.PagedResponse;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.UpdateProductRequest;
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

@Tag(name = "products-admin", description = "Product management operations for administrators")
@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductAdminController {
  private final WebCatalogOrchestratorService internalCatalogService;

  public ProductAdminController(WebCatalogOrchestratorService internalCatalogService) {
    this.internalCatalogService = internalCatalogService;
  }

  @Operation(operationId = "getAllProducts", summary = "Get all products", description = "Get paginated list of all products (admin only)")
  @GetMapping
  public ResponseEntity<PagedResponse<ProductResponse>> getAllProducts(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int perPage) {
    PageResult<Product> pageResult = internalCatalogService.getAllProductsPaged(page, perPage);
    PagedResponse<ProductResponse> response = PagedResponse.from(pageResult, ProductResponse::from);
    return ResponseEntity.ok(response);
  }

  @Operation(operationId = "getProductById", summary = "Get product by ID", description = "Get a single product by its ID (admin only)")
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
    ProductId productId = ProductId.of(id);
    Optional<Product> product = internalCatalogService.getProduct(productId);
    return product.map(p -> ResponseEntity.ok(ProductResponse.from(p)))
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(operationId = "createProduct", summary = "Create new product", description = "Create a new product (admin only)")
  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
    try {
      CreateProductUseCase.CreateProductCommand command = new CreateProductUseCase.CreateProductCommand(
          request.name(),
          request.description(),
          Money.of(request.price(), request.currency()),
          request.stock(),
          CategoryId.of(request.categoryId()),
          request.images()
      );

      Product product = internalCatalogService.createProduct(command);
      return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.from(product));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "updateProduct", summary = "Update product", description = "Update an existing product (admin only)")
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest request) {
    try {
      ProductId productId = ProductId.of(id);
      UpdateProductUseCase.UpdateProductCommand command = new UpdateProductUseCase.UpdateProductCommand(
          productId,
          request.name(),
          request.description(),
          request.price() != null ? Money.of(request.price().amount(), request.price().currency()) : null,
          request.stock(),
          request.categoryId() != null ? CategoryId.of(request.categoryId()) : null
      );

      Product product = internalCatalogService.updateProduct(command);
      return ResponseEntity.ok(ProductResponse.from(product));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "deleteProduct", summary = "Delete product", description = "Delete a product by its ID (admin only)")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
    try {
      ProductId productId = ProductId.of(id);
      internalCatalogService.deleteProduct(productId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
