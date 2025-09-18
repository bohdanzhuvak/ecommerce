package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.time.LocalDateTime;

public class UserUpdated {
  private final UserId userId;
  private final String firstName;
  private final String lastName;
  private final LocalDateTime occurredAt;

  public UserUpdated(UserId userId, String firstName, String lastName) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.occurredAt = LocalDateTime.now();
  }

  public UserId getUserId() {
    return userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
