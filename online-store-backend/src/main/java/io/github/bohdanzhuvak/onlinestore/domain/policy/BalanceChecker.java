package io.github.bohdanzhuvak.onlinestore.domain.policy;

import java.math.BigDecimal;

public interface BalanceChecker {
  boolean hasSufficientFunds(Long userId, BigDecimal amount);
}
