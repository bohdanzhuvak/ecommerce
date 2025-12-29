package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  @Column(nullable = false)
  private boolean active;

  @ElementCollection
  @CollectionTable(
      name = "user_delivery_addresses",
      joinColumns = @JoinColumn(name = "user_id")
  )
  private List<DeliveryAddressEmbeddable> addresses;

  @Column(nullable = true)
  private String defaultAddressId;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  // Default constructor for JPA
  protected UserEntity() {
  }

  // Constructor for creating new entity
  public UserEntity(String id, String email, String passwordHash, String firstName,
                    String lastName, UserRole role, boolean active, List<DeliveryAddressEmbeddable> addresses,
                    String defaultAddressId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.email = email;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.active = active;
    this.addresses = addresses;
    this.defaultAddressId = defaultAddressId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<DeliveryAddressEmbeddable> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<DeliveryAddressEmbeddable> addresses) {
    this.addresses = addresses;
  }

  public String getDefaultAddressId() {
    return defaultAddressId;
  }

  public void setDefaultAddressId(String defaultAddressId) {
    this.defaultAddressId = defaultAddressId;
  }
}
