package io.github.bohdanzhuvak.onlinestore.auth.application.usecase;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.TokenService;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.application.result.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthenticationSession;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;

import java.util.Optional;

/**
 * Use case for refreshing access tokens
 */
public class RefreshTokenUseCase {

  private final TokenService tokenService;
  private final AuthRepository authRepository;
  private final UserInfoPort userInfoPort;

  public RefreshTokenUseCase(TokenService tokenService,
                             AuthRepository authRepository,
                             UserInfoPort userInfoPort) {
    this.tokenService = tokenService;
    this.authRepository = authRepository;
    this.userInfoPort = userInfoPort;
  }

  public AuthenticationResult execute(RefreshTokenCommand command) {
    RefreshToken refreshToken = RefreshToken.of(command.refreshToken(), null);
    // Validate refresh token
    if (!tokenService.isRefreshTokenValid(refreshToken)) {
      throw new IllegalArgumentException("Invalid refresh token");
    }

    // Extract user info from refresh token
    TokenService.UserInfo tokenUserInfo = tokenService.extractUserInfo(refreshToken)
        .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

    // Verify user still exists and is active
    User user = userInfoPort.findById(tokenUserInfo.id());

    if (!user.isActive()) {
      throw new IllegalArgumentException("User account is not active");
    }

    // Generate new tokens
    TokenPair tokenPair = tokenService.generateTokenPair(user);

    // Update or create new session
    Optional<AuthenticationSession> existingSession = authRepository.findByRefreshToken(refreshToken);
    if (existingSession.isPresent()) {
      // Terminate old session
      AuthenticationSession oldSession = existingSession.get();
      oldSession.terminate();
      authRepository.save(oldSession);
    }

    // Create new session
    AuthenticationSession newSession = AuthenticationSession.create(user);
    authRepository.save(newSession);

    // Return new authentication result
    return new AuthenticationResult(user, tokenPair);
  }

  public record RefreshTokenCommand(String refreshToken) {
  }
}
