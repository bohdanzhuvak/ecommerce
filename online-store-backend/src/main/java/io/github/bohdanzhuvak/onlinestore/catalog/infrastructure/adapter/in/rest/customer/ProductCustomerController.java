package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.catalog.application.services.WebCatalogOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "products-customer", description = "Product browsing and search for customers")
@RestController
@RequestMapping("/api/v1/customer/products")
public class ProductCustomerController {
  private final WebCatalogOrchestratorService internalCatalogService;

  public ProductCustomerController(WebCatalogOrchestratorService internalCatalogService) {
    this.internalCatalogService = internalCatalogService;
  }

  @Operation(operationId = "getProductsForCustomer", summary = "Get products", description = "Get products list for customer browsing")
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getProducts(
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    List<Product> products = internalCatalogService.getActiveProducts(offset, limit);
    return ResponseEntity.ok(ProductResponse.from(products));
  }

  @Operation(operationId = "getProductForCustomer", summary = "Get product by ID", description = "Get a single product by its ID for customer")
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
    ProductId productId = ProductId.of(id);
    Product product = internalCatalogService.getActiveProduct(productId);
    return ResponseEntity.ok(ProductResponse.from(product));
  }

  @Operation(operationId = "searchProducts", summary = "Search products", description = "Search products by name, category, or price range")
  @GetMapping("/search")
  public ResponseEntity<List<ProductResponse>> searchProducts(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String categoryId,
      @RequestParam(required = false) String minPrice,
      @RequestParam(required = false) String maxPrice,
      @RequestParam(required = false) String currency) {

    List<Product> products;

    if (name != null && !name.trim().isEmpty()) {
      products = internalCatalogService.searchProductsByName(name);
    } else if (categoryId != null && !categoryId.trim().isEmpty()) {
      products = internalCatalogService.searchProductsByCategory(CategoryId.of(categoryId));
    } else if (minPrice != null && maxPrice != null && currency != null) {
      Money min = Money.of(Double.parseDouble(minPrice), currency);
      Money max = Money.of(Double.parseDouble(maxPrice), currency);
      products = internalCatalogService.searchProductsByPriceRange(min, max);
    } else {
      products = internalCatalogService.getActiveProducts();
    }

    return ResponseEntity.ok(ProductResponse.from(products));
  }

  @Operation(operationId = "getAvailableProducts", summary = "Get available products", description = "Get all products that are in stock and available for purchase")
  @GetMapping("/available")
  public ResponseEntity<List<ProductResponse>> getAvailableProducts() {
    List<Product> products = internalCatalogService.searchAvailableProducts();
    return ResponseEntity.ok(ProductResponse.from(products));
  }
}
