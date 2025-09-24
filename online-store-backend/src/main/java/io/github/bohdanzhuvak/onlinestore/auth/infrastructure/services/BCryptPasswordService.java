package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.services;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordService implements PasswordHasher {

  @Override
  public Password hash(String rawPassword) {
    String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    return Password.ofHashed(hashed);
  }

  @Override
  public boolean matches(String rawPassword, Password hashedPassword) {
    return BCrypt.checkpw(rawPassword, hashedPassword.getHashedValue());
  }
}
