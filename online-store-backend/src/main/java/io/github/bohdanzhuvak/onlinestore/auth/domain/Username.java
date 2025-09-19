package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Username {
  private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 50;

  private final String value;

  private Username(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    String trimmedValue = value.trim();
    if (trimmedValue.length() < MIN_LENGTH || trimmedValue.length() > MAX_LENGTH) {
      throw new IllegalArgumentException("Username must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
    }
    if (!USERNAME_PATTERN.matcher(trimmedValue).matches()) {
      throw new IllegalArgumentException("Username can only contain letters, numbers and underscores");
    }
    this.value = trimmedValue;
  }

  public static Username of(String value) {
    return new Username(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Username username = (Username) o;
    return Objects.equals(value, username.value);
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
