package io.github.bohdanzhuvak.onlinestore.auth.application.result;

import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;

/**
 * Value Object representing the result of authentication
 */
public record AuthenticationResult(User user, TokenPair tokenPair) {
  public AuthenticationResult {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    if (tokenPair == null) {
      throw new IllegalArgumentException("Token pair cannot be null");
    }
  }

}
