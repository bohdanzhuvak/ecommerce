package io.github.bohdanzhuvak.onlinestore.auth.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.PasswordHasher;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.TokenService;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.application.result.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthenticationSession;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;

/**
 * Use case for user registration
 */
@UseCase
public class RegisterUseCase {

  private final UserInfoPort userInfoPort;
  private final TokenService tokenService;
  private final AuthRepository authRepository;
  private final PasswordHasher passwordHasher;

  public RegisterUseCase(UserInfoPort userInfoPort,
                         TokenService tokenService,
                         AuthRepository authRepository,
                         PasswordHasher passwordHasher) {
    this.userInfoPort = userInfoPort;
    this.tokenService = tokenService;
    this.authRepository = authRepository;
    this.passwordHasher = passwordHasher;
  }

  public AuthenticationResult execute(RegisterCommand command) {
    Email email = Email.of(command.email());
    // Check if user already exists
    if (userInfoPort.existsByEmail(email)) {
      throw new IllegalArgumentException("User with email already exists: " + command.email());
    }
    Password password = passwordHasher.hash(command.password());
    // Create user (this should hash the password)
    User user = userInfoPort.createUser(email,
        password,
        command.firstName(),
        command.lastName());

    // Generate tokens
    TokenPair tokenPair = tokenService.generateTokenPair(user);

    // Create authentication session
    AuthenticationSession session = AuthenticationSession.create(user);

    // Save session
    authRepository.save(session);

    // Return authentication result
    return new AuthenticationResult(user, tokenPair);
  }

  public record RegisterCommand(
      String email,
      String password,
      String firstName,
      String lastName) {
  }
}
