package io.github.bohdanzhuvak.onlinestore.balance.domain;

import java.time.LocalDateTime;

public class InsufficientFunds {
  private final UserId userId;
  private final Money requestedAmount;
  private final Money availableBalance;
  private final String description;
  private final LocalDateTime occurredAt;

  public InsufficientFunds(UserId userId, Money requestedAmount, Money availableBalance, String description) {
    this.userId = userId;
    this.requestedAmount = requestedAmount;
    this.availableBalance = availableBalance;
    this.description = description;
    this.occurredAt = LocalDateTime.now();
  }

  public UserId getUserId() {
    return userId;
  }

  public Money getRequestedAmount() {
    return requestedAmount;
  }

  public Money getAvailableBalance() {
    return availableBalance;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
