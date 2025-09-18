package io.github.bohdanzhuvak.onlinestore.delivery.domain;

import java.util.Objects;
import java.util.UUID;

public class DeliveryId {
  private final String value;

  private DeliveryId(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Delivery ID cannot be null or empty");
    }
    this.value = value;
  }

  public static DeliveryId generate() {
    return new DeliveryId(UUID.randomUUID().toString());
  }

  public static DeliveryId of(String value) {
    return new DeliveryId(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeliveryId that = (DeliveryId) o;
    return Objects.equals(value, that.value);
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
