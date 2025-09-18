package io.github.bohdanzhuvak.onlinestore.balance.infrastructure;

import io.github.bohdanzhuvak.onlinestore.balance.application.BalanceApplicationService;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.BalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceAdapter implements BalanceService {
  private final BalanceApplicationService balanceApplicationService;

  public BalanceServiceAdapter(BalanceApplicationService balanceApplicationService) {
    this.balanceApplicationService = balanceApplicationService;
  }

  @Override
  public boolean hasSufficientFunds(String userId, BigDecimal amount) {
    try {
      var balance = balanceApplicationService.getBalance(UserId.of(userId));
      return balance.hasSufficientFunds(Money.of(amount, "USD"));
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void deductBalance(String userId, BigDecimal amount, String description) {
    balanceApplicationService.withdraw(
        UserId.of(userId),
        Money.of(amount, "USD"),
        description
    );
  }

  @Override
  public void addBalance(String userId, BigDecimal amount, String description) {
    balanceApplicationService.deposit(
        UserId.of(userId),
        Money.of(amount, "USD"),
        description
    );
  }
}
