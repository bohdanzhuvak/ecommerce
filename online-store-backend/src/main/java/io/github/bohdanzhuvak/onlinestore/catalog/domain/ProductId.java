package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.util.Objects;
import java.util.UUID;

public class ProductId {
  private final String value;

  private ProductId(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }
    this.value = value;
  }

  public static ProductId generate() {
    return new ProductId(UUID.randomUUID().toString());
  }

  public static ProductId of(String value) {
    return new ProductId(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductId productId = (ProductId) o;
    return Objects.equals(value, productId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
