package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.time.LocalDateTime;

public class UserCreated {
  private final UserId userId;
  private final Email email;
  private final String firstName;
  private final String lastName;
  private final UserRole role;
  private final LocalDateTime occurredAt;

  public UserCreated(UserId userId, Email email, String firstName, String lastName, UserRole role) {
    this.userId = userId;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.occurredAt = LocalDateTime.now();
  }

  public UserId getUserId() {
    return userId;
  }

  public Email getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public UserRole getRole() {
    return role;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
