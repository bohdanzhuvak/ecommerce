package io.github.bohdanzhuvak.onlinestore.order.application.ports;

import java.math.BigDecimal;

public interface BalanceService {
  /**
   * Checks if user has sufficient funds
   *
   * @param userId the user ID
   * @param amount the amount to check
   * @return true if user has sufficient funds
   */
  boolean hasSufficientFunds(String userId, BigDecimal amount);

  /**
   * Deducts balance from user account
   *
   * @param userId      the user ID
   * @param amount      the amount to deduct
   * @param description the transaction description
   */
  void deductBalance(String userId, BigDecimal amount, String description);

  /**
   * Adds balance to user account
   *
   * @param userId      the user ID
   * @param amount      the amount to add
   * @param description the transaction description
   */
  void addBalance(String userId, BigDecimal amount, String description);
}
