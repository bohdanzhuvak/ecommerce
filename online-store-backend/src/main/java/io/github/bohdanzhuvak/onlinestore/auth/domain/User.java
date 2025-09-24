package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.Objects;

public class User {
  private final UserId id;
  private final Email email;
  private final Password password;
  private final UserRole role;
  private boolean active;
  private String firstName;
  private String lastName;

  private User(UserId id, Email email, Password password, UserRole role, String firstName, String lastName, boolean active) {
    if (id == null) {
      throw new IllegalArgumentException("User ID cannot be null");
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
    if (firstName == null) {
      throw new IllegalArgumentException("First name cannot be null");
    }
    if (lastName == null) {
      throw new IllegalArgumentException("Last name cannot be null");
    }

    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.active = active;
  }

  public static User create(Email email, Password password, UserRole role, String firstName, String lastName) {
    return new User(
        UserId.generate(),
        email,
        password,
        role,
        firstName,
        lastName,
        true
    );
  }

  public static User restore(UserId id, Email email, Password password,
                             UserRole role, String firstName, String lastName, boolean active) {
    return new User(id, email, password, role, firstName, lastName, active);
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

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public UserRole getRole() {
    return role;
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
        ", email=" + email +
        ", role=" + role +
        ", active=" + active +
        '}';
  }
}
