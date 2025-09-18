package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
  );

  private final String value;

  private Email(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    String trimmedValue = value.trim().toLowerCase();
    if (!EMAIL_PATTERN.matcher(trimmedValue).matches()) {
      throw new IllegalArgumentException("Invalid email format: " + value);
    }
    this.value = trimmedValue;
  }

  public static Email of(String value) {
    return new Email(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Email email = (Email) o;
    return Objects.equals(value, email.value);
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
