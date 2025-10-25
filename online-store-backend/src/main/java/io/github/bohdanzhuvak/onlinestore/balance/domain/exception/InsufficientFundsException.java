package io.github.bohdanzhuvak.onlinestore.balance.domain.exception;

import io.github.bohdanzhuvak.onlinestore.architecture.BusinessRuleViolationException;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

/**
 * Exception thrown when attempting to debit an amount that exceeds the available balance.
 * <p>
 * This is a domain-specific business rule violation that occurs when a user
 * attempts to make a purchase or withdrawal with insufficient funds.
 * </p>
 */
public class InsufficientFundsException extends BusinessRuleViolationException {

  private static final String ERROR_CODE = "INSUFFICIENT_FUNDS";

  private final UserId userId;
  private final Money requestedAmount;
  private final Money availableBalance;

  public InsufficientFundsException(UserId userId, Money requestedAmount, Money availableBalance) {
    super(
        ERROR_CODE,
        String.format(
            "Insufficient funds for user %s. Requested: %s, Available: %s",
            userId.getValue(),
            requestedAmount.getAmount(),
            availableBalance.getAmount()
        )
    );
    this.userId = userId;
    this.requestedAmount = requestedAmount;
    this.availableBalance = availableBalance;
  }

  public UserId getUserId() {
    return userId;
  }

  public Money getRequestedAmount() {
    return requestedAmount;
  }

  public Money getAvailableBalance() {
    return availableBalance;
  }
}
