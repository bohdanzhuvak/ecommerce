package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  /**
   * Saves a user
   *
   * @param user the user to save
   * @return the saved user
   */
  User save(User user);

  /**
   * Finds user by ID
   *
   * @param id the user ID
   * @return the user if found, empty otherwise
   */
  Optional<User> findById(UserId id);

  /**
   * Finds user by email
   *
   * @param email the email
   * @return the user if found, empty otherwise
   */
  Optional<User> findByEmail(Email email);

  /**
   * Finds user by username
   *
   * @param username the username
   * @return the user if found, empty otherwise
   */
  Optional<User> findByUsername(Username username);

  /**
   * Finds users by role
   *
   * @param role the user role
   * @return list of users with the specified role
   */
  List<User> findByRole(UserRole role);

  /**
   * Finds users by role with pagination
   *
   * @param role   the user role
   * @param offset the offset
   * @param limit  the limit
   * @return list of users with the specified role and pagination
   */
  List<User> findByRole(UserRole role, int offset, int limit);

  /**
   * Finds all users with pagination
   *
   * @param offset the offset
   * @param limit  the limit
   * @return list of users with pagination
   */
  List<User> findAll(int offset, int limit);

  /**
   * Counts users by role
   *
   * @param role the user role
   * @return number of users with the specified role
   */
  long countByRole(UserRole role);

  /**
   * Counts total users
   *
   * @return total number of users
   */
  long count();

  /**
   * Checks if user exists by ID
   *
   * @param id the user ID
   * @return true if user exists, false otherwise
   */
  boolean existsById(UserId id);

  /**
   * Checks if user exists by email
   *
   * @param email the email
   * @return true if user exists, false otherwise
   */
  boolean existsByEmail(Email email);

  /**
   * Checks if user exists by username
   *
   * @param username the username
   * @return true if user exists, false otherwise
   */
  boolean existsByUsername(Username username);

  /**
   * Deletes user
   *
   * @param id the user ID
   */
  void delete(UserId id);
}
