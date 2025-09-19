package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
  private final UserId id;
  private final Username username;
  private final Email email;
  private final Password password;
  private final UserRole role;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private boolean active;

  private User(UserId id, Username username, Email email, Password password, UserRole role,
               LocalDateTime createdAt, LocalDateTime updatedAt, boolean active) {
    if (id == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    if (username == null) {
      throw new IllegalArgumentException("Username cannot be null");
    }
    if (email == null) {
      throw new IllegalArgumentException("Email cannot be null");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }
    if (role == null) {
      throw new IllegalArgumentException("Role cannot be null");
    }
    if (createdAt == null) {
      throw new IllegalArgumentException("Created at cannot be null");
    }
    if (updatedAt == null) {
      throw new IllegalArgumentException("Updated at cannot be null");
    }

    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.active = active;
  }

  public static User create(Username username, Email email, Password password, UserRole role) {
    LocalDateTime now = LocalDateTime.now();
    return new User(
        UserId.generate(),
        username,
        email,
        password,
        role,
        now,
        now,
        true
    );
  }

  public static User restore(UserId id, Username username, Email email, Password password,
                             UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt,
                             boolean active) {
    return new User(id, username, email, password, role, createdAt, updatedAt, active);
  }

  // Business methods
  public void activate() {
    this.active = true;
  }

  public void deactivate() {
    this.active = false;
  }

  public void changePassword(Password newPassword) {
    if (newPassword == null) {
      throw new IllegalArgumentException("New password cannot be null");
    }
    // In a real implementation, this would update the password and set updatedAt
    // For now, we'll just validate the state
  }

  public void changeEmail(Email newEmail) {
    if (newEmail == null) {
      throw new IllegalArgumentException("New email cannot be null");
    }
    // In a real implementation, this would update the email and set updatedAt
    // For now, we'll just validate the state
  }

  public void changeUsername(Username newUsername) {
    if (newUsername == null) {
      throw new IllegalArgumentException("New username cannot be null");
    }
    // In a real implementation, this would update the username and set updatedAt
    // For now, we'll just validate the state
  }

  public boolean isActive() {
    return active;
  }

  public boolean hasRole(UserRole role) {
    return this.role.equals(role);
  }

  public boolean isAdmin() {
    return role.isAdmin();
  }

  public boolean isUser() {
    return role.isUser();
  }

  // Getters
  public UserId getId() {
    return id;
  }

  public Username getUsername() {
    return username;
  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public UserRole getRole() {
    return role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username=" + username +
        ", email=" + email +
        ", role=" + role +
        ", active=" + active +
        '}';
  }
}
