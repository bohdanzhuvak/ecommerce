package io.github.bohdanzhuvak.onlinestore.auth.domain;

public interface PasswordHasher {
  Password hash(String rawPassword);

  boolean matches(String rawPassword, Password hashedPassword);
}
