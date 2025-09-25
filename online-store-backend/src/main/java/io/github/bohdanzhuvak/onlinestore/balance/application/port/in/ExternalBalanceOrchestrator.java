package io.github.bohdanzhuvak.onlinestore.balance.application.port.in;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

public interface ExternalBalanceOrchestrator {

  boolean hasSufficientFunds(UserId userId, Money amount);

  void debitBalanceForPurchase(UserId userId, Money amount, String description);

  void creditBalanceForRefund(UserId userId, Money amount, String description);
}
