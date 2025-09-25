package io.github.bohdanzhuvak.onlinestore.balance.application.port.out;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

import java.util.Optional;

public interface BalanceRepository {
  /**
   * Saves a balance
   *
   * @param balance the balance to save
   * @return the saved balance
   */
  Balance save(Balance balance);

  /**
   * Finds balance by user ID
   *
   * @param userId the user ID
   * @return the balance if found, empty otherwise
   */
  Optional<Balance> findByUserId(UserId userId);

  /**
   * Checks if balance exists for user
   *
   * @param userId the user ID
   * @return true if balance exists, false otherwise
   */
  boolean existsByUserId(UserId userId);

  /**
   * Deletes balance for user
   *
   * @param userId the user ID
   */
  void deleteByUserId(UserId userId);
}
