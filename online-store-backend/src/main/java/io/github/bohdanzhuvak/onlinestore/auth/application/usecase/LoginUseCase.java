package io.github.bohdanzhuvak.onlinestore.auth.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.PasswordHasher;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.TokenService;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.application.result.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthenticationSession;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;

/**
 * Use case for user login
 */
@UseCase
public class LoginUseCase {

  private final UserInfoPort userInfoPort;
  private final TokenService tokenService;
  private final AuthRepository authRepository;
  private final PasswordHasher passwordHasher;

  public LoginUseCase(UserInfoPort userInfoPort,
                      TokenService tokenService,
                      AuthRepository authRepository,
                      PasswordHasher passwordHasher) {
    this.userInfoPort = userInfoPort;
    this.tokenService = tokenService;
    this.authRepository = authRepository;
    this.passwordHasher = passwordHasher;
  }

  public AuthenticationResult execute(LoginCommand command) {
    Email email = Email.of(command.email);

    // Validate credentials (password is validated in user module)
    if (!userInfoPort.validateCredentials(email, command.password)) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    // Get user information
    User user = userInfoPort.findByEmail(email);

    if (!user.isActive()) {
      throw new IllegalArgumentException("User account is not active");
    }

    // Generate tokens
    TokenPair tokenPair = tokenService.generateTokenPair(user);

    // Create authentication session
    AuthenticationSession session = AuthenticationSession.create(user);

    // Save session
    authRepository.save(session);

    // Return authentication result
    return new AuthenticationResult(user, tokenPair);
  }

  public record LoginCommand(String email, String password) {
  }
}
