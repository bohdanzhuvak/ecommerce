package io.github.bohdanzhuvak.onlinestore.auth.application.port.out;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Credentials;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;

/**
 * Service interface for user operations from User bounded context
 * This is an anti-corruption layer to avoid direct dependency on User context
 */
public interface UserInfoPort {

  /**
   * Finds a user by email
   *
   * @param email the user email
   * @return the user if found, empty otherwise
   */
  User findByEmail(Email email);

  /**
   * Finds a user by ID
   *
   * @param userId the user ID
   * @return the user if found, empty otherwise
   */
  User findById(UserId userId);

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
   * @param email    the user email
   * @param password the hashed password
   * @return the created user info
   */
  User createUser(Email email, Password password, String firstName, String lastName);

  /**
   * Validates user credentials
   *
   * @param credentials@return true if credentials are valid, false otherwise
   */
  boolean validateCredentials(Credentials credentials);
}
