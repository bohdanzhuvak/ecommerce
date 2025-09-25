package io.github.bohdanzhuvak.onlinestore.order.infrastructure.balance;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.ExternalBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.BalancePort;
import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class BalancePortAdapter implements BalancePort {
  private final ExternalBalanceOrchestrator externalBalanceOrchestrator;

  public BalancePortAdapter(ExternalBalanceOrchestrator externalBalanceOrchestrator) {
    this.externalBalanceOrchestrator = externalBalanceOrchestrator;
  }

  @Override
  public boolean hasSufficientFunds(UserId userId, Money amount) {
    return externalBalanceOrchestrator.hasSufficientFunds(toBalanceUserId(userId), toBalanceMoney(amount));
  }

  private io.github.bohdanzhuvak.onlinestore.balance.domain.Money toBalanceMoney(Money amount) {
    return io.github.bohdanzhuvak.onlinestore.balance.domain.Money.of(amount.getAmount(), amount.getCurrency());
  }

  private io.github.bohdanzhuvak.onlinestore.balance.domain.UserId toBalanceUserId(UserId userId) {
    return io.github.bohdanzhuvak.onlinestore.balance.domain.UserId.of(userId.getValue());
  }

  @Override
  public void debitBalanceForPurchase(UserId userId, Money amount, String description) {
    externalBalanceOrchestrator.debitBalanceForPurchase(toBalanceUserId(userId), toBalanceMoney(amount), description);
  }

  @Override
  public void creditBalanceForRefund(UserId userId, Money amount, String description) {
    externalBalanceOrchestrator.creditBalanceForRefund(toBalanceUserId(userId), toBalanceMoney(amount), description);
  }

}
