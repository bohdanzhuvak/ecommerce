package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.Optional;

/**
 * Service interface for token operations
 */
public interface TokenService {

  TokenPair generateTokenPair(User user);

  /**
   * Validates an access token
   *
   * @param token the access token
   * @return true if valid, false otherwise
   */
  boolean isAccessTokenValid(AccessToken token);

  /**
   * Validates a refresh token
   *
   * @param token the refresh token
   * @return true if valid, false otherwise
   */
  boolean isRefreshTokenValid(RefreshToken token);

  /**
   * Extracts user information from an access token
   *
   * @param token the access token
   * @return user information if token is valid, empty otherwise
   */
  Optional<UserInfo> extractUserInfo(AccessToken token);

  /**
   * Extracts user information from a refresh token
   *
   * @param token the refresh token
   * @return user information if token is valid, empty otherwise
   */
  Optional<UserInfo> extractUserInfo(RefreshToken token);

  /**
   * User information extracted from token
   */
  record UserInfo(
      UserId id,
      Email email,
      UserRole role
  ) {
  }
}
