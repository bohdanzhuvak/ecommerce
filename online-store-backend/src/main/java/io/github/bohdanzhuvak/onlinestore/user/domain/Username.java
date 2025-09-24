package io.github.bohdanzhuvak.onlinestore.user.domain;

public record Username(String firstName, String lastName) {
  public Username(String firstName, String lastName) {
    if (firstName == null || firstName.trim().isEmpty()) {
      throw new IllegalArgumentException("First name cannot be null or empty");
    }
    if (lastName == null || lastName.trim().isEmpty()) {
      throw new IllegalArgumentException("Last name cannot be null or empty");
    }
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
  }


  @Override
  public String toString() {
    return "Username{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
