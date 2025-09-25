package io.github.bohdanzhuvak.onlinestore.auth.application.port.out;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;

public interface PasswordHasher {
  Password hash(String rawPassword);

  boolean matches(String rawPassword, Password hashedPassword);
}
