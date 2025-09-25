package io.github.bohdanzhuvak.onlinestore.auth.application.usecase;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthenticationSession;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;

import java.util.Optional;

/**
 * Use case for user logout
 */
public class LogoutUseCase {

  private final AuthRepository authRepository;

  public LogoutUseCase(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  public void execute(LogoutCommand command) {
    // Find session by refresh token
    Optional<AuthenticationSession> session = authRepository.findByRefreshToken(RefreshToken.of(command.refreshToken(), null));

    if (session.isPresent()) {
      // Terminate the session
      AuthenticationSession authSession = session.get();
      authSession.terminate();
      authRepository.save(authSession);
    }
  }

  public record LogoutCommand(String refreshToken) {
  }
}
