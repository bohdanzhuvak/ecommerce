package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for product response
 */
public record ProductResponse(
    String id,
    String name,
    String description,
    MoneyResponse price,
    int stock,
    String categoryId,
    List<String> images,
    boolean active,
    LocalDateTime createdAt,
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
      BigDecimal amount,
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
