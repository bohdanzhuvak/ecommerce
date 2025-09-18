package io.github.bohdanzhuvak.onlinestore.delivery.domain;

import java.util.Objects;
import java.util.UUID;

public class UserId {
  private final String value;

  private UserId(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    this.value = value;
  }

  public static UserId generate() {
    return new UserId(UUID.randomUUID().toString());
  }

  public static UserId of(String value) {
    return new UserId(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserId userId = (UserId) o;
    return Objects.equals(value, userId.value);
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
