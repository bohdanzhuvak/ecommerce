package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.Objects;

/**
 * Value Object representing a pair of access and refresh tokens
 */
public final class TokenPair {
  private final AccessToken accessToken;
  private final RefreshToken refreshToken;

  private TokenPair(AccessToken accessToken, RefreshToken refreshToken) {
    if (accessToken == null) {
      throw new IllegalArgumentException("Access token cannot be null");
    }
    if (refreshToken == null) {
      throw new IllegalArgumentException("Refresh token cannot be null");
    }

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static TokenPair of(AccessToken accessToken, RefreshToken refreshToken) {
    return new TokenPair(accessToken, refreshToken);
  }

  public AccessToken getAccessToken() {
    return accessToken;
  }

  public RefreshToken getRefreshToken() {
    return refreshToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TokenPair tokenPair = (TokenPair) o;
    return Objects.equals(accessToken, tokenPair.accessToken) &&
        Objects.equals(refreshToken, tokenPair.refreshToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessToken, refreshToken);
  }

  @Override
  public String toString() {
    return "TokenPair{" +
        "accessToken=" + accessToken +
        ", refreshToken=" + refreshToken +
        '}';
  }
}
