package io.github.bohdanzhuvak.onlinestore.balance.domain.exception;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

/**
 * Exception thrown when a balance record cannot be found for a given user.
 * <p>
 * This typically indicates a data integrity issue, as every user should have
 * an associated balance record. This exception helps identify cases where
 * the balance initialization might have failed.
 * </p>
 */
public class BalanceNotFoundException extends ResourceNotFoundException {

  private static final String RESOURCE_TYPE = "Balance";

  public BalanceNotFoundException(UserId userId) {
    super(RESOURCE_TYPE, userId.getValue());
  }

  public BalanceNotFoundException(String userId) {
    super(RESOURCE_TYPE, userId);
  }
}
