package io.github.bohdanzhuvak.onlinestore.auth.domain;

public enum UserRole {
  CUSTOMER("CUSTOMER"),
  ADMIN("ADMIN");

  private final String value;

  UserRole(String value) {
    this.value = value;
  }

  public static UserRole fromString(String value) {
    if (value == null) {
      throw new IllegalArgumentException("User role cannot be null");
    }
    for (UserRole role : values()) {
      if (role.value.equalsIgnoreCase(value.trim())) {
        return role;
      }
    }
    throw new IllegalArgumentException("Invalid user role: " + value);
  }

  public String getValue() {
    return value;
  }

  public boolean isUser() {
    return this == CUSTOMER;
  }

  public boolean isAdmin() {
    return this == ADMIN;
  }

  public boolean hasAdminPrivileges() {
    return this == ADMIN;
  }

  @Override
  public String toString() {
    return value;
  }
}
