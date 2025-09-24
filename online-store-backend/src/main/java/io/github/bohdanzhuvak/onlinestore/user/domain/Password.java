package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.util.Objects;

public class Password {
  private final String hashedValue;

  private Password(String hashedValue) {
    if (hashedValue == null || hashedValue.trim().isEmpty()) {
      throw new IllegalArgumentException("Password hash cannot be null or empty");
    }
    this.hashedValue = hashedValue;
  }

  public static Password ofHashed(String hashedValue) {
    return new Password(hashedValue);
  }

  public static Password fromPlainText(String plainText) {
    if (plainText == null || plainText.length() < 8) {
      throw new IllegalArgumentException("Password must be at least 8 characters long");
    }
    // In real implementation, this would hash the password
    // For now, we'll just store it as-is (NOT RECOMMENDED FOR PRODUCTION)
    return new Password(plainText);
  }

  public String getHashedValue() {
    return hashedValue;
  }

  public boolean matches(Password password) {
    return hashedValue.equals(password.hashedValue);
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
    return "Password{hashed}";
  }
}
