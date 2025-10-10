package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.balance;

import io.github.bohdanzhuvak.onlinestore.domain.policy.BalanceChecker;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BalanceServiceAdapter implements BalanceChecker {

  private final BalanceService balanceService;

  @Override
  public boolean hasSufficientFunds(Long userId, BigDecimal amount) {
    return balanceService.hasSufficientFunds(userId, amount);
  }
}
