package io.github.bohdanzhuvak.onlinestore.catalog.api.admin;

import io.github.bohdanzhuvak.onlinestore.catalog.application.InternalCatalogService;
import io.github.bohdanzhuvak.onlinestore.catalog.application.admin.CreateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.admin.UpdateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/products")
public class ProductAdminController {
  private final InternalCatalogService internalCatalogService;

  public ProductAdminController(InternalCatalogService internalCatalogService) {
    this.internalCatalogService = internalCatalogService;
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts(
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    List<Product> products = internalCatalogService.getAllProducts(offset, limit);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable String id) {
    ProductId productId = ProductId.of(id);
    Optional<Product> product = internalCatalogService.getProduct(productId);
    return product.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
    try {
      CreateProductUseCase.CreateProductCommand command = new CreateProductUseCase.CreateProductCommand(
          request.getName(),
          request.getDescription(),
          Money.of(request.getPrice(), request.getCurrency()),
          request.getStock(),
          CategoryId.of(request.getCategoryId()),
          request.getImages()
      );

      Product product = internalCatalogService.createProduct(command);
      return ResponseEntity.status(HttpStatus.CREATED).body(product);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest request) {
    try {
      ProductId productId = ProductId.of(id);
      UpdateProductUseCase.UpdateProductCommand command = new UpdateProductUseCase.UpdateProductCommand(
          productId,
          request.getName(),
          request.getDescription(),
          request.getPrice() != null ? Money.of(request.getPrice(), request.getCurrency()) : null,
          request.getStock(),
          request.getCategoryId() != null ? CategoryId.of(request.getCategoryId()) : null
      );

      Product product = internalCatalogService.updateProduct(command);
      return ResponseEntity.ok(product);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

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

  // DTO классы для запросов
  public static class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String currency;
    private Integer stock;
    private String categoryId;
    private List<String> images;

    // Геттеры и сеттеры
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public Double getPrice() {
      return price;
    }

    public void setPrice(Double price) {
      this.price = price;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public Integer getStock() {
      return stock;
    }

    public void setStock(Integer stock) {
      this.stock = stock;
    }

    public String getCategoryId() {
      return categoryId;
    }

    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }

    public List<String> getImages() {
      return images;
    }

    public void setImages(List<String> images) {
      this.images = images;
    }
  }

  public static class UpdateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String currency;
    private Integer stock;
    private String categoryId;

    // Геттеры и сеттеры
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public Double getPrice() {
      return price;
    }

    public void setPrice(Double price) {
      this.price = price;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public Integer getStock() {
      return stock;
    }

    public void setStock(Integer stock) {
      this.stock = stock;
    }

    public String getCategoryId() {
      return categoryId;
    }

    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }
  }
}
