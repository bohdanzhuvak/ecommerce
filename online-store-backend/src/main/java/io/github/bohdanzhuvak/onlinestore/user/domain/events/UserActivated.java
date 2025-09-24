package io.github.bohdanzhuvak.onlinestore.user.domain.events;

import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

import java.time.LocalDateTime;

public class UserActivated {
  private final UserId userId;
  private final LocalDateTime occurredAt;

  public UserActivated(UserId userId) {
    this.userId = userId;
    this.occurredAt = LocalDateTime.now();
  }

  public UserId getUserId() {
    return userId;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
