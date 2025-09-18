package io.github.bohdanzhuvak.onlinestore.balance.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Balance {
  private final UserId userId;
  private final Money amount;
  private final LocalDateTime lastUpdated;

  private Balance(UserId userId, Money amount, LocalDateTime lastUpdated) {
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    if (amount == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }
    if (amount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Balance cannot be negative");
    }
    if (lastUpdated == null) {
      throw new IllegalArgumentException("Last updated cannot be null");
    }

    this.userId = userId;
    this.amount = amount;
    this.lastUpdated = lastUpdated;
  }

  public static Balance of(UserId userId, Money amount) {
    return new Balance(userId, amount, LocalDateTime.now());
  }

  public static Balance restore(UserId userId, Money amount, LocalDateTime lastUpdated) {
    return new Balance(userId, amount, lastUpdated);
  }

  public Balance add(Money amount) {
    if (!this.amount.getCurrency().equals(amount.getCurrency())) {
      throw new IllegalArgumentException("Cannot add money with different currencies");
    }
    Money newAmount = this.amount.add(amount);
    return new Balance(this.userId, newAmount, LocalDateTime.now());
  }

  public Balance subtract(Money amount) {
    if (!this.amount.getCurrency().equals(amount.getCurrency())) {
      throw new IllegalArgumentException("Cannot subtract money with different currencies");
    }
    Money newAmount = this.amount.subtract(amount);
    if (newAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }
    return new Balance(this.userId, newAmount, LocalDateTime.now());
  }

  public boolean hasSufficientFunds(Money amount) {
    if (!this.amount.getCurrency().equals(amount.getCurrency())) {
      throw new IllegalArgumentException("Cannot compare money with different currencies");
    }
    return this.amount.isGreaterThanOrEqual(amount);
  }

  public boolean isZero() {
    return this.amount.getAmount().compareTo(BigDecimal.ZERO) == 0;
  }

  public boolean belongsTo(UserId userId) {
    return this.userId.equals(userId);
  }

  // Getters
  public UserId getUserId() {
    return userId;
  }

  public Money getAmount() {
    return amount;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Balance balance = (Balance) o;
    return Objects.equals(userId, balance.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @Override
  public String toString() {
    return "Balance{" +
        "userId=" + userId +
        ", amount=" + amount +
        ", lastUpdated=" + lastUpdated +
        '}';
  }
}
