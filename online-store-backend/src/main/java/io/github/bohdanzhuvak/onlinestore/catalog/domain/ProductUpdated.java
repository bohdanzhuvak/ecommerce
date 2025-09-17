package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.time.LocalDateTime;

public class ProductUpdated {
  private final ProductId productId;
  private final String name;
  private final Money price;
  private final CategoryId categoryId;
  private final LocalDateTime occurredAt;

  public ProductUpdated(ProductId productId, String name, Money price, CategoryId categoryId) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.occurredAt = LocalDateTime.now();
  }

  public ProductId getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
