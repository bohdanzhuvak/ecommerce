package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.time.LocalDateTime;

public class UserLoggedIn {
  private final UserId userId;
  private final Username username;
  private final Email email;
  private final UserRole role;
  private final LocalDateTime occurredAt;

  public UserLoggedIn(UserId userId, Username username, Email email, UserRole role) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.role = role;
    this.occurredAt = LocalDateTime.now();
  }

  public UserId getUserId() {
    return userId;
  }

  public Username getUsername() {
    return username;
  }

  public Email getEmail() {
    return email;
  }

  public UserRole getRole() {
    return role;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
