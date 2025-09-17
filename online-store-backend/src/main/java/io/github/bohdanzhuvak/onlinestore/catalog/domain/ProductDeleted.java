package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.time.LocalDateTime;

public class ProductDeleted {
  private final ProductId productId;
  private final LocalDateTime occurredAt;

  public ProductDeleted(ProductId productId) {
    this.productId = productId;
    this.occurredAt = LocalDateTime.now();
  }

  public ProductId getProductId() {
    return productId;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
