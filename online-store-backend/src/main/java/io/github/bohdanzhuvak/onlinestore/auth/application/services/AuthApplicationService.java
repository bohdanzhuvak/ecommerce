package io.github.bohdanzhuvak.onlinestore.auth.application.services;

import io.github.bohdanzhuvak.onlinestore.auth.application.results.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.LoginUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.LogoutUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.RefreshTokenUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.RegisterUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for authentication operations
 * Orchestrates use cases and provides a unified interface
 */
@Service
@Transactional
public class AuthApplicationService {

  private final LoginUseCase loginUseCase;
  private final RegisterUseCase registerUseCase;
  private final RefreshTokenUseCase refreshTokenUseCase;
  private final LogoutUseCase logoutUseCase;

  public AuthApplicationService(LoginUseCase loginUseCase,
                                RegisterUseCase registerUseCase,
                                RefreshTokenUseCase refreshTokenUseCase,
                                LogoutUseCase logoutUseCase) {
    this.loginUseCase = loginUseCase;
    this.registerUseCase = registerUseCase;
    this.refreshTokenUseCase = refreshTokenUseCase;
    this.logoutUseCase = logoutUseCase;
  }

  /**
   * Login user with credentials
   */
  public AuthenticationResult login(String email, String password) {
    LoginUseCase.LoginCommand command = new LoginUseCase.LoginCommand(email, password);
    return loginUseCase.execute(command);
  }

  /**
   * Register new user
   */
  public AuthenticationResult register(String email, String password, String firstName, String lastName) {
    RegisterUseCase.RegisterCommand command = new RegisterUseCase.RegisterCommand(email, password, firstName, lastName);
    return registerUseCase.execute(command);
  }

  /**
   * Refresh access token using refresh token
   */
  public AuthenticationResult refreshToken(String refreshToken) {
    RefreshTokenUseCase.RefreshTokenCommand command = new RefreshTokenUseCase.RefreshTokenCommand(refreshToken);
    return refreshTokenUseCase.execute(command);
  }

  /**
   * Logout user
   */
  public void logout(String refreshToken) {
    LogoutUseCase.LogoutCommand command = new LogoutUseCase.LogoutCommand(refreshToken);
    logoutUseCase.execute(command);
  }
}
