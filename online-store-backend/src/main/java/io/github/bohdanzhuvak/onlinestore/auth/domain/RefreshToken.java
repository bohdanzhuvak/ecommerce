package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.time.Instant;
import java.util.Objects;

public class RefreshToken {
  private final String value;
  private final Instant expiresAt;

  private RefreshToken(String value, Instant expiresAt) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Refresh token cannot be null or empty");
    }
    if (expiresAt == null) {
      throw new IllegalArgumentException("Expires at cannot be null");
    }
    this.value = value;
    this.expiresAt = expiresAt;
  }

  public static RefreshToken of(String value, Instant expiresAt) {
    return new RefreshToken(value, expiresAt);
  }

  public String getValue() {
    return value;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  public boolean isExpired() {
    return Instant.now().isAfter(expiresAt);
  }

  public boolean isValid() {
    return !isExpired();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RefreshToken that = (RefreshToken) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "RefreshToken{value='[HIDDEN]', expiresAt=" + expiresAt + "}";
  }
}
