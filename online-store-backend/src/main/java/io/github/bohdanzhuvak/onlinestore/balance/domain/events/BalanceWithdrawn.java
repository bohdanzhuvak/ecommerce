package io.github.bohdanzhuvak.onlinestore.balance.domain.events;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

import java.time.LocalDateTime;

public class BalanceWithdrawn {
  private final UserId userId;
  private final Money amount;
  private final Money newBalance;
  private final String description;
  private final LocalDateTime occurredAt;

  public BalanceWithdrawn(UserId userId, Money amount, Money newBalance, String description) {
    this.userId = userId;
    this.amount = amount;
    this.newBalance = newBalance;
    this.description = description;
    this.occurredAt = LocalDateTime.now();
  }

  public UserId getUserId() {
    return userId;
  }

  public Money getAmount() {
    return amount;
  }

  public Money getNewBalance() {
    return newBalance;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
