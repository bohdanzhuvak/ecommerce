package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.adapters.external;

import io.github.bohdanzhuvak.onlinestore.auth.application.ports.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Credentials;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import io.github.bohdanzhuvak.onlinestore.user.application.ports.UserQuery;

/**
 * Anti-corruption layer between Auth context and User context
 */
public class UserInfoAdapter implements UserInfoPort {

  private final UserQuery userQuery;

  public UserInfoAdapter(UserQuery userQuery) {
    this.userQuery = userQuery;
  }

  @Override
  public User findByEmail(Email email) {
    UserQuery.UserInfo userInfo = userQuery.findByEmail(toUserEmail(email));
    return toAuthUser(userInfo);
  }

  @Override
  public User findById(UserId userId) {
    UserQuery.UserInfo userInfo = userQuery.findById(toUserId(userId));
    return toAuthUser(userInfo);
  }

  @Override
  public boolean existsByEmail(Email email) {
    return userQuery.existsByEmail(toUserEmail(email));
  }

  @Override
  public User createUser(Email email, Password password, String firstName, String lastName) {
    UserQuery.UserInfo userInfo =
        userQuery.createUser(toUserEmail(email), toUserPassword(password), firstName, lastName);

    return toAuthUser(userInfo);
  }

  @Override
  public boolean validateCredentials(Credentials credentials) {
    return userQuery.validateCredentials(toUserEmail(credentials.getEmail()), toUserPassword(credentials.getPassword()));
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

  private User toAuthUser(UserQuery.UserInfo userInfo) {
    return User.restore(
        UserId.of(userInfo.id().getValue()),
        Email.of(userInfo.email().getValue()),
        null,
        UserRole.fromString(userInfo.role().getValue()),
        userInfo.firstName(),
        userInfo.lastName(),
        userInfo.active()
    );
  }
}
