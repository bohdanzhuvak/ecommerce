package io.github.bohdanzhuvak.onlinestore.balance.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
  private final BigDecimal amount;
  private final String currency;

  private Money(BigDecimal amount, String currency) {
    if (amount == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }
    if (currency == null || currency.trim().isEmpty()) {
      throw new IllegalArgumentException("Currency cannot be null or empty");
    }

    this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    this.currency = currency.toUpperCase();
  }

  public static Money of(BigDecimal amount, String currency) {
    return new Money(amount, currency);
  }

  public static Money of(double amount, String currency) {
    return new Money(BigDecimal.valueOf(amount), currency);
  }

  public static Money zero(String currency) {
    return new Money(BigDecimal.ZERO, currency);
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public Money add(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot add money with different currencies");
    }
    return new Money(this.amount.add(other.amount), this.currency);
  }

  public Money subtract(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot subtract money with different currencies");
    }
    return new Money(this.amount.subtract(other.amount), this.currency);
  }

  public Money negate() {
    return new Money(this.amount.negate(), this.currency);
  }

  public boolean isGreaterThan(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot compare money with different currencies");
    }
    return this.amount.compareTo(other.amount) > 0;
  }

  public boolean isLessThan(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot compare money with different currencies");
    }
    return this.amount.compareTo(other.amount) < 0;
  }

  public boolean isGreaterThanOrEqual(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot compare money with different currencies");
    }
    return this.amount.compareTo(other.amount) >= 0;
  }

  public boolean isLessThanOrEqual(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException("Cannot compare money with different currencies");
    }
    return this.amount.compareTo(other.amount) <= 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }

  @Override
  public String toString() {
    return amount + " " + currency;
  }
}
