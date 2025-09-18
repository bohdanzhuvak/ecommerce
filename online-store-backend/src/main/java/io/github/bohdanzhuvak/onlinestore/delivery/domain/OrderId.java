package io.github.bohdanzhuvak.onlinestore.delivery.domain;

import java.util.Objects;
import java.util.UUID;

public class OrderId {
  private final String value;

  private OrderId(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Order ID cannot be null or empty");
    }
    this.value = value;
  }

  public static OrderId generate() {
    return new OrderId(UUID.randomUUID().toString());
  }

  public static OrderId of(String value) {
    return new OrderId(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderId orderId = (OrderId) o;
    return Objects.equals(value, orderId.value);
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
