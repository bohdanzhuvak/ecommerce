package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.auth.application.result.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for authentication response
 */
public record AuthResponse(
    @Schema(description = "Access token for authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    String token,

    @Schema(description = "User information", requiredMode = Schema.RequiredMode.REQUIRED)
    UserInfo user,

    @Schema(description = "Token expiration information", requiredMode = Schema.RequiredMode.REQUIRED)
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
      @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED)
      String id,

      @Schema(description = "User email address", requiredMode = Schema.RequiredMode.REQUIRED)
      String email,

      @Schema(description = "User role", requiredMode = Schema.RequiredMode.REQUIRED)
      String role
  ) {
  }

  public record TokenInfo(
      @Schema(description = "Access token expiration timestamp in milliseconds", requiredMode = Schema.RequiredMode.REQUIRED)
      long accessExpiresAt,

      @Schema(description = "Refresh token expiration timestamp in milliseconds", requiredMode = Schema.RequiredMode.REQUIRED)
      long refreshExpiresAt
  ) {
  }
}
