package io.github.bohdanzhuvak.onlinestore.user.application.port.out;

import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;

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
   * Finds a user by ID
   *
   * @param id the user ID
   * @return the user if found, empty otherwise
   */
  Optional<User> findById(UserId id);

  /**
   * Finds a user by email
   *
   * @param email the user email
   * @return the user if found, empty otherwise
   */
  Optional<User> findByEmail(Email email);

  /**
   * Finds all users
   *
   * @return list of all users
   */
  List<User> findAll();

  /**
   * Finds users by role
   *
   * @param role the user role
   * @return list of users with the specified role
   */
  List<User> findByRole(UserRole role);

  /**
   * Finds active users
   *
   * @return list of active users
   */
  List<User> findActiveUsers();

  /**
   * Finds inactive users
   *
   * @return list of inactive users
   */
  List<User> findInactiveUsers();

  /**
   * Finds users by name containing the given text
   *
   * @param name the name to search for
   * @return list of users whose name contains the given text
   */
  List<User> findByNameContaining(String name);

  /**
   * Finds users with pagination
   *
   * @param offset the offset
   * @param limit  the limit
   * @return list of users with pagination
   */
  List<User> findAll(int offset, int limit);

  /**
   * Finds active users with pagination
   *
   * @param offset the offset
   * @param limit  the limit
   * @return list of active users with pagination
   */
  List<User> findActiveUsers(int offset, int limit);

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
   * Counts total users
   *
   * @return total number of users
   */
  long count();

  /**
   * Counts active users
   *
   * @return number of active users
   */
  long countActiveUsers();

  /**
   * Counts users by role
   *
   * @param role the user role
   * @return number of users with the specified role
   */
  long countByRole(UserRole role);

  /**
   * Checks if a user exists by ID
   *
   * @param id the user ID
   * @return true if user exists, false otherwise
   */
  boolean existsById(UserId id);

  /**
   * Checks if a user exists by email
   *
   * @param email the user email
   * @return true if user exists, false otherwise
   */
  boolean existsByEmail(Email email);

  /**
   * Deletes a user
   *
   * @param id the user ID
   */
  void delete(UserId id);
}
