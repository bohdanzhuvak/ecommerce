package io.github.bohdanzhuvak.onlinestore.architecture;

/**
 * Base exception for all domain-related exceptions.
 * Represents violations of business rules and domain logic.
 * <p>
 * This is the root of the exception hierarchy in our hexagonal architecture.
 * All domain exceptions should extend this class to maintain clear separation
 * between domain logic errors and technical/infrastructure errors.
 * </p>
 */
public abstract class DomainException extends RuntimeException {

  protected DomainException(String message) {
    super(message);
  }

  protected DomainException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Returns the error code for this exception.
   * Error codes are used for client-side error handling and i18n.
   *
   * @return the error code
   */
  public abstract String getErrorCode();
}
