package io.github.bohdanzhuvak.onlinestore.order.application.ports.out;

import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

public interface BalancePort {
  /**
   * Checks if user has sufficient funds
   *
   * @param userId the user ID
   * @param amount the amount to check
   * @return true if user has sufficient funds
   */
  boolean hasSufficientFunds(UserId userId, Money amount);

  /**
   * Deducts balance from user account
   *
   * @param userId      the user ID
   * @param amount      the amount to deduct
   * @param description the transaction description
   */
  void debitBalanceForPurchase(UserId userId, Money amount, String description);

  /**
   * Adds balance to user account
   *
   * @param userId      the user ID
   * @param amount      the amount to add
   * @param description the transaction description
   */
  void creditBalanceForRefund(UserId userId, Money amount, String description);
}
