package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
  private final UserId id;
  private final Email email;
  private final Password password;
  private final String firstName;
  private final String lastName;
  private List<DeliveryAddress> addresses;
  private DeliveryAddressId defaultAddressId;
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
               UserRole role, boolean active, List<DeliveryAddress> deliveryAddresses, DeliveryAddressId defaultAddressId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.active = active;
    this.addresses = deliveryAddresses;
    this.defaultAddressId = defaultAddressId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for restoring from database
  public static User restore(UserId id, Email email, Password password, String firstName, String lastName,
                             UserRole role, boolean active, List<DeliveryAddress> deliveryAddresses, DeliveryAddressId defaultAddressId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new User(id, email, password, firstName, lastName, role, active, deliveryAddresses, defaultAddressId, createdAt, updatedAt);
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

  public void updateAddress(DeliveryAddress newAddress) {
    if (newAddress == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }

    List<DeliveryAddress> updatedAddresses = new ArrayList<>();
    for (DeliveryAddress address : addresses) {
      if (address.id().equals(newAddress.id())) {
        DeliveryAddress updated = new DeliveryAddress(
            address.id(),
            newAddress.street(),
            newAddress.city(),
            newAddress.state(),
            newAddress.postalCode(),
            newAddress.country(),
            newAddress.recipientName(),
            address.createdAt()
        );
        updatedAddresses.add(updated);
      } else {
        updatedAddresses.add(address);
      }
    }

    this.addresses = updatedAddresses;
  }

  public void addAddress(DeliveryAddress address) {
    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }
    addresses.add(address);
  }

  public void removeAddress(DeliveryAddressId id) {
    if (id == null) {
      throw new IllegalArgumentException("Address ID cannot be null");
    }
    addresses.removeIf(address -> address.id().equals(id));
  }

  public void setDefaultAddress(DeliveryAddressId id) {
    if (id == null) {
      throw new IllegalArgumentException("Address ID cannot be null");
    }
    boolean found = addresses.stream().anyMatch(address -> address.id().equals(id));
    if (!found) {
      throw new IllegalArgumentException("Address ID not found in user's addresses");
    }
    defaultAddressId = id;
  }

  public List<DeliveryAddress> getAddresses() {
    return List.copyOf(addresses);
  }

  public DeliveryAddressId getDefaultAddressId() {
    return defaultAddressId;
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
