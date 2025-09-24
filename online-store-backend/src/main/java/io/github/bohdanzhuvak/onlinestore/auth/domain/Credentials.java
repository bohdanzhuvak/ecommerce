package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.Objects;

/**
 * Value Object representing user credentials for authentication
 */
public final class Credentials {
  private final Email email;
  private final Password password;

  private Credentials(Email email, Password password) {
    if (email == null) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    this.email = email;
    this.password = password;
  }

  public static Credentials of(Email email, Password password) {
    return new Credentials(email, password);
  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Credentials that = (Credentials) o;
    return Objects.equals(email.getValue(), that.email.getValue()) && Objects.equals(password.getHashedValue(), that.password.getHashedValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  @Override
  public String toString() {
    return "Credentials{" +
        "email='" + email.getValue() + '\'' +
        ", password='[HIDDEN]'" +
        '}';
  }
}
