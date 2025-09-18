package io.github.bohdanzhuvak.onlinestore.balance.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
  private final TransactionId id;
  private final UserId userId;
  private final TransactionType type;
  private final Money amount;
  private final Money balanceAfter;
  private final String description;
  private final String orderId;
  private final LocalDateTime createdAt;

  private Transaction(TransactionId id, UserId userId, TransactionType type, Money amount,
                      Money balanceAfter, String description, String orderId, LocalDateTime createdAt) {
    if (id == null) {
      throw new IllegalArgumentException("Transaction ID cannot be null");
    }
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    if (type == null) {
      throw new IllegalArgumentException("Transaction type cannot be null");
    }
    if (amount == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }
    if (balanceAfter == null) {
      throw new IllegalArgumentException("Balance after cannot be null");
    }
    if (createdAt == null) {
      throw new IllegalArgumentException("Created at cannot be null");
    }

    this.id = id;
    this.userId = userId;
    this.type = type;
    this.amount = amount;
    this.balanceAfter = balanceAfter;
    this.description = description;
    this.orderId = orderId;
    this.createdAt = createdAt;
  }

  public static Transaction create(UserId userId, TransactionType type, Money amount,
                                   Money balanceAfter, String description) {
    return new Transaction(
        TransactionId.generate(),
        userId,
        type,
        amount,
        balanceAfter,
        description,
        null,
        LocalDateTime.now()
    );
  }

  public static Transaction createWithOrder(UserId userId, TransactionType type, Money amount,
                                            Money balanceAfter, String description, String orderId) {
    return new Transaction(
        TransactionId.generate(),
        userId,
        type,
        amount,
        balanceAfter,
        description,
        orderId,
        LocalDateTime.now()
    );
  }

  public static Transaction restore(TransactionId id, UserId userId, TransactionType type,
                                    Money amount, Money balanceAfter, String description,
                                    String orderId, LocalDateTime createdAt) {
    return new Transaction(id, userId, type, amount, balanceAfter, description, orderId, createdAt);
  }

  public boolean isDebit() {
    return type.isDebit();
  }

  public boolean isCredit() {
    return type.isCredit();
  }

  public boolean isRelatedToOrder() {
    return orderId != null;
  }

  // Getters
  public TransactionId getId() {
    return id;
  }

  public UserId getUserId() {
    return userId;
  }

  public TransactionType getType() {
    return type;
  }

  public Money getAmount() {
    return amount;
  }

  public Money getBalanceAfter() {
    return balanceAfter;
  }

  public String getDescription() {
    return description;
  }

  public String getOrderId() {
    return orderId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "id=" + id +
        ", userId=" + userId +
        ", type=" + type +
        ", amount=" + amount +
        ", balanceAfter=" + balanceAfter +
        ", description='" + description + '\'' +
        ", orderId='" + orderId + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
