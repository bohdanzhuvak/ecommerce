package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.user;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import io.github.bohdanzhuvak.onlinestore.user.application.port.in.ExternalUserOrchestrator;
import org.springframework.stereotype.Service;

/**
 * Anti-corruption layer between Auth context and User context
 */
@Service
public class UserInfoAdapter implements UserInfoPort {

  private final ExternalUserOrchestrator externalUserOrchestrator;

  public UserInfoAdapter(ExternalUserOrchestrator externalUserOrchestrator) {
    this.externalUserOrchestrator = externalUserOrchestrator;
  }

  @Override
  public User findByEmail(Email email) {
    ExternalUserOrchestrator.UserInfo userInfo = externalUserOrchestrator.findByEmail(toUserEmail(email));
    return toAuthUser(userInfo);
  }

  @Override
  public User findById(UserId userId) {
    ExternalUserOrchestrator.UserInfo userInfo = externalUserOrchestrator.findById(toUserId(userId));
    return toAuthUser(userInfo);
  }

  @Override
  public boolean existsByEmail(Email email) {
    return externalUserOrchestrator.existsByEmail(toUserEmail(email));
  }

  @Override
  public User createUser(Email email, Password password, String firstName, String lastName) {
    ExternalUserOrchestrator.UserInfo userInfo =
        externalUserOrchestrator.createUser(toUserEmail(email), toUserPassword(password), firstName, lastName);

    return toAuthUser(userInfo);
  }

  @Override
  public boolean validateCredentials(Email email, String rawPassword) {
    return externalUserOrchestrator.validateCredentials(toUserEmail(email), rawPassword);
  }

  // ============================
  // Mapping helpers
  // ============================

  private io.github.bohdanzhuvak.onlinestore.user.domain.Email toUserEmail(Email email) {
    return io.github.bohdanzhuvak.onlinestore.user.domain.Email.of(email.getValue());
  }

  private io.github.bohdanzhuvak.onlinestore.user.domain.Password toUserPassword(Password password) {
    return io.github.bohdanzhuvak.onlinestore.user.domain.Password.ofHashed(password.getHashedValue());
  }

  private io.github.bohdanzhuvak.onlinestore.user.domain.UserId toUserId(UserId userId) {
    return io.github.bohdanzhuvak.onlinestore.user.domain.UserId.of(userId.getValue());
  }

  private User toAuthUser(ExternalUserOrchestrator.UserInfo userInfo) {
    return User.restore(
        UserId.of(userInfo.id().getValue()),
        Email.of(userInfo.email().getValue()),
        UserRole.fromString(userInfo.role().getValue()),
        userInfo.firstName(),
        userInfo.lastName(),
        userInfo.active()
    );
  }
}
