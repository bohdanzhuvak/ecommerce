package io.github.bohdanzhuvak.onlinestore.user.application.ports;


import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;

public interface UserQuery {

  /**
   * Finds a user by email
   *
   * @param email the user email
   * @return the user if found, empty otherwise
   */
  UserInfo findByEmail(Email email);

  /**
   * Finds a user by ID
   *
   * @param userId the user ID
   * @return the user if found, empty otherwise
   */
  UserInfo findById(UserId userId);

  /**
   * Checks if a user exists by email
   *
   * @param email the user email
   * @return true if user exists, false otherwise
   */
  boolean existsByEmail(Email email);

  /**
   * Creates a new user
   *
   * @param email     the user email
   * @param password  the hashed password
   * @param firstName the first name
   * @param lastName  the last name
   * @return the created user info
   */
  UserInfo createUser(Email email, Password password, String firstName, String lastName);

  /**
   * Validates user credentials
   *
   * @param email    the user email
   * @param password the plain password
   * @return true if credentials are valid, false otherwise
   */
  boolean validateCredentials(Email email, Password password);

  record UserInfo(
      UserId id,
      Email email,
      UserRole role,
      String firstName,
      String lastName,
      boolean active
  ) {
  }
}
