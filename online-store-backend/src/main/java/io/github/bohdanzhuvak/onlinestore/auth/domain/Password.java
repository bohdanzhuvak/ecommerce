package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.Objects;

public class Password {
  private final String hashedValue;

  private Password(String hashedValue) {
    if (hashedValue == null || hashedValue.trim().isEmpty()) {
      throw new IllegalArgumentException("Hashed password cannot be null or empty");
    }
    this.hashedValue = hashedValue;
  }

  public static Password ofHashed(String hashedValue) {
    return new Password(hashedValue);
  }

  public static Password ofRaw(String rawPassword) {
    if (rawPassword == null || rawPassword.trim().isEmpty()) {
      throw new IllegalArgumentException("Raw password cannot be null or empty");
    }
    if (rawPassword.length() < 6) {
      throw new IllegalArgumentException("Password must be at least 6 characters long");
    }
    // In a real implementation, this would hash the password
    // For now, we'll just store it as-is (this should be handled by the application layer)
    throw new UnsupportedOperationException("Raw password hashing should be handled by the application layer");
  }

  public String getHashedValue() {
    return hashedValue;
  }

  public boolean matches(String rawPassword) {
    // In a real implementation, this would use BCrypt or similar
    // For now, we'll assume the application layer handles this
    throw new UnsupportedOperationException("Password matching should be handled by the application layer");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Password password = (Password) o;
    return Objects.equals(hashedValue, password.hashedValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hashedValue);
  }

  @Override
  public String toString() {
    return "Password{hashedValue='[HIDDEN]'}";
  }
}
