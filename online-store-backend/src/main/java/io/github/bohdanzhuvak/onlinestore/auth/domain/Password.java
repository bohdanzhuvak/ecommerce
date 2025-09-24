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

  public String getHashedValue() {
    return hashedValue;
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
