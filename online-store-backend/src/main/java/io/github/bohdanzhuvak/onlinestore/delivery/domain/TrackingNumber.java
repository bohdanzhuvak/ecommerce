package io.github.bohdanzhuvak.onlinestore.delivery.domain;

import java.util.Objects;
import java.util.UUID;

public class TrackingNumber {
  private final String value;

  private TrackingNumber(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Tracking number cannot be null or empty");
    }
    this.value = value;
  }

  public static TrackingNumber generate() {
    return new TrackingNumber("TRK" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase());
  }

  public static TrackingNumber of(String value) {
    return new TrackingNumber(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TrackingNumber that = (TrackingNumber) o;
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
