package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for product response
 */
public record ProductResponse(
    @Schema(description = "Product ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED)
    String name,

    @Schema(description = "Product description", requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Product price", requiredMode = Schema.RequiredMode.REQUIRED)
    MoneyResponse price,

    @Schema(description = "Available stock quantity", requiredMode = Schema.RequiredMode.REQUIRED)
    int stock,

    @Schema(description = "Category ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String categoryId,

    @Schema(description = "Product image URLs", requiredMode = Schema.RequiredMode.REQUIRED)
    List<String> images,

    @Schema(description = "Whether product is active", requiredMode = Schema.RequiredMode.REQUIRED)
    boolean active,

    @Schema(description = "Creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime updatedAt
) {

  public static ProductResponse from(Product product) {
    return new ProductResponse(
        product.getId().getValue(),
        product.getName(),
        product.getDescription(),
        MoneyResponse.from(product.getPrice()),
        product.getStock(),
        product.getCategoryId().getValue(),
        product.getImages(),
        product.isActive(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }

  public static List<ProductResponse> from(List<Product> products) {
    return products.stream()
        .map(ProductResponse::from)
        .toList();
  }

  public record MoneyResponse(
      @Schema(description = "Amount value", requiredMode = Schema.RequiredMode.REQUIRED)
      BigDecimal amount,

      @Schema(description = "Currency code", requiredMode = Schema.RequiredMode.REQUIRED)
      String currency
  ) {

    public static MoneyResponse from(io.github.bohdanzhuvak.onlinestore.catalog.domain.Money money) {
      return new MoneyResponse(
          money.getAmount(),
          money.getCurrency()
      );
    }
  }
}
