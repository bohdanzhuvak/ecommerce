package io.github.bohdanzhuvak.onlinestore.architecture;

/**
 * Exception thrown when a business rule is violated.
 * <p>
 * This exception represents violations of domain invariants and business constraints.
 * It should be used when an operation cannot be completed because it would violate
 * business rules defined in the domain model.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *   if (!balance.hasSufficientFunds(amount)) {
 *     throw new BusinessRuleViolationException(
 *       "INSUFFICIENT_FUNDS",
 *       "Cannot debit amount greater than available balance"
 *     );
 *   }
 * </pre>
 * </p>
 */
public class BusinessRuleViolationException extends DomainException {

  private final String errorCode;

  public BusinessRuleViolationException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BusinessRuleViolationException(String errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }
}
