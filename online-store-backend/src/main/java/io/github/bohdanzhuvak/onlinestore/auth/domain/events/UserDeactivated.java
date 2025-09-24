package io.github.bohdanzhuvak.onlinestore.auth.domain.events;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;

import java.time.LocalDateTime;

public class UserDeactivated {
  private final UserId userId;
  private final Username username;
  private final Email email;
  private final LocalDateTime occurredAt;

  public UserDeactivated(UserId userId, Username username, Email email) {
    this.userId = userId;
    this.username = username;
    this.email = email;
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

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
