package io.github.bohdanzhuvak.onlinestore.auth.application;

import io.github.bohdanzhuvak.onlinestore.auth.application.admin.ActivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.admin.DeactivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.admin.GetAllUsersUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.customer.GetUserUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.customer.LoginUserUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.customer.RegisterUserUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.customer.UpdateUserProfileUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AccessToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthApplicationService {
  private final RegisterUserUseCase registerUserUseCase;
  private final LoginUserUseCase loginUserUseCase;
  private final GetUserUseCase getUserUseCase;
  private final UpdateUserProfileUseCase updateUserProfileUseCase;
  private final GetAllUsersUseCase getAllUsersUseCase;
  private final ActivateUserUseCase activateUserUseCase;
  private final DeactivateUserUseCase deactivateUserUseCase;

  public AuthApplicationService(RegisterUserUseCase registerUserUseCase,
                                LoginUserUseCase loginUserUseCase,
                                GetUserUseCase getUserUseCase,
                                UpdateUserProfileUseCase updateUserProfileUseCase,
                                GetAllUsersUseCase getAllUsersUseCase,
                                ActivateUserUseCase activateUserUseCase,
                                DeactivateUserUseCase deactivateUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
    this.loginUserUseCase = loginUserUseCase;
    this.getUserUseCase = getUserUseCase;
    this.updateUserProfileUseCase = updateUserProfileUseCase;
    this.getAllUsersUseCase = getAllUsersUseCase;
    this.activateUserUseCase = activateUserUseCase;
    this.deactivateUserUseCase = deactivateUserUseCase;
  }

  // Customer operations
  public User registerUser(Username username, Email email, Password password) {
    RegisterUserUseCase.RegisterUserCommand command = new RegisterUserUseCase.RegisterUserCommand(
        username, email, password);
    return registerUserUseCase.execute(command);
  }

  public Optional<User> loginUser(Email email, Password password) {
    LoginUserUseCase.LoginUserCommand command = new LoginUserUseCase.LoginUserCommand(email, password);
    return loginUserUseCase.execute(command);
  }

  public Optional<User> getUser(UserId userId) {
    return getUserUseCase.execute(userId);
  }

  public User updateUserProfile(UserId userId, Username username, Email email, Password password) {
    UpdateUserProfileUseCase.UpdateUserProfileCommand command = new UpdateUserProfileUseCase.UpdateUserProfileCommand(
        userId, username, email, password);
    return updateUserProfileUseCase.execute(command);
  }

  // Admin operations
  public List<User> getAllUsers(UserRole role, int offset, int limit) {
    GetAllUsersUseCase.GetAllUsersCommand command = new GetAllUsersUseCase.GetAllUsersCommand(
        role, offset, limit);
    return getAllUsersUseCase.execute(command);
  }

  public User activateUser(UserId userId) {
    return activateUserUseCase.execute(userId);
  }

  public User deactivateUser(UserId userId) {
    return deactivateUserUseCase.execute(userId);
  }

  // Token operations (these would typically be in a separate service)
  public AccessToken generateAccessToken(User user, long ttlMillis) {
    // This would typically use JWT service
    String tokenValue = "access_token_" + user.getId().getValue() + "_" + Instant.now().toEpochMilli();
    Instant expiresAt = Instant.now().plusMillis(ttlMillis);
    return AccessToken.of(tokenValue, expiresAt);
  }

  public RefreshToken generateRefreshToken(User user, long ttlMillis) {
    // This would typically use JWT service
    String tokenValue = "refresh_token_" + user.getId().getValue() + "_" + Instant.now().toEpochMilli();
    Instant expiresAt = Instant.now().plusMillis(ttlMillis);
    return RefreshToken.of(tokenValue, expiresAt);
  }
}
