package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balances")
public class BalanceEntity {
  @Id
  private String userId;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false)
  private LocalDateTime lastUpdated;

  // Default constructor for JPA
  protected BalanceEntity() {
  }

  // Constructor for creating new entity
  public BalanceEntity(String userId, BigDecimal amount, String currency, LocalDateTime lastUpdated) {
    this.userId = userId;
    this.amount = amount;
    this.currency = currency;
    this.lastUpdated = lastUpdated;
  }

  // Getters and setters
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }
}
