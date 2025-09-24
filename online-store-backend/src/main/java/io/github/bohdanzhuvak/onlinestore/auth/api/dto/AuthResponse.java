package io.github.bohdanzhuvak.onlinestore.auth.api.dto;

import io.github.bohdanzhuvak.onlinestore.auth.application.results.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;

/**
 * DTO for authentication response
 */
public record AuthResponse(
    String token,
    UserInfo user,
    TokenInfo tokenInfo
) {

  public static AuthResponse from(AuthenticationResult authResult) {
    TokenPair tokenPair = authResult.tokenPair();
    User user = authResult.user();
    return new AuthResponse(
        tokenPair.getAccessToken().getValue(),
        new UserInfo(
            user.getId().getValue(),
            user.getEmail().getValue(),
            user.getRole().getValue()
        ),
        new TokenInfo(
            tokenPair.getAccessToken().getExpiresAt().toEpochMilli(),
            tokenPair.getRefreshToken().getExpiresAt().toEpochMilli()
        )
    );
  }

  public record UserInfo(
      String id,
      String email,
      String role
  ) {
  }

  public record TokenInfo(
      long accessExpiresAt,
      long refreshExpiresAt
  ) {
  }
}
