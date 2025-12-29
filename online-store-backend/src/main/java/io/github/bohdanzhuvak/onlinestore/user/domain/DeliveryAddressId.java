package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.util.Objects;
import java.util.UUID;

public class DeliveryAddressId {
  private final String value;

  private DeliveryAddressId(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Delivery address id cannot be null or empty");
    }
    this.value = value;
  }

  public static DeliveryAddressId generate() {
    return new DeliveryAddressId(UUID.randomUUID().toString());
  }

  public static DeliveryAddressId of(String value) {
    return new DeliveryAddressId(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeliveryAddressId deliveryAddressId = (DeliveryAddressId) o;
    return Objects.equals(value, deliveryAddressId.value);
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
