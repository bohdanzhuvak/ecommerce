package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
  private final UserId id;
  private final Email email;
  private final Password password;
  private final String firstName;
  private final String lastName;
  private final UserRole role;
  private final boolean active;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  // Constructor for creating a new user
  public User(UserId id, Email email, Password password, String firstName, String lastName, UserRole role) {
    if (id == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    if (email == null) {
      throw new IllegalArgumentException("Email cannot be null");
    }
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }
    if (firstName == null || firstName.trim().isEmpty()) {
      throw new IllegalArgumentException("First name cannot be null or empty");
    }
    if (lastName == null || lastName.trim().isEmpty()) {
      throw new IllegalArgumentException("Last name cannot be null or empty");
    }
    if (role == null) {
      throw new IllegalArgumentException("User role cannot be null");
    }

    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
    this.role = role;
    this.active = true;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Constructor for restoring from database
  private User(UserId id, Email email, Password password, String firstName, String lastName,
               UserRole role, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.active = active;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for restoring from database
  public static User restore(UserId id, Email email, Password password, String firstName, String lastName,
                             UserRole role, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new User(id, email, password, firstName, lastName, role, active, createdAt, updatedAt);
  }

  // Business methods
  public void updateProfile(String firstName, String lastName) {
    if (firstName == null || firstName.trim().isEmpty()) {
      throw new IllegalArgumentException("First name cannot be null or empty");
    }
    if (lastName == null || lastName.trim().isEmpty()) {
      throw new IllegalArgumentException("Last name cannot be null or empty");
    }
    // In a real implementation, this would update the fields and set updatedAt
    // For now, we'll just validate the input
  }

  public void changePassword(Password newPassword) {
    if (newPassword == null) {
      throw new IllegalArgumentException("New password cannot be null");
    }
    // In a real implementation, this would update the password and set updatedAt
    // For now, we'll just validate the input
  }

  public void activate() {
    if (!this.active) {
      // In a real implementation, this would set active to true and update updatedAt
      // For now, we'll just validate the state
    }
  }

  public void deactivate() {
    if (this.active) {
      // In a real implementation, this would set active to false and update updatedAt
      // For now, we'll just validate the state
    }
  }

  public boolean isActive() {
    return active;
  }

  public boolean isAdmin() {
    return role.isAdmin();
  }

  public boolean isCustomer() {
    return role.isCustomer();
  }

  public String getFullName() {
    return firstName + " " + lastName;
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

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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
        ", email=" + email +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", role=" + role +
        ", active=" + active +
        '}';
  }

  public boolean isPasswordValid(Password password) {
    return this.password.equals(password);
  }
}
