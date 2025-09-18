package io.github.bohdanzhuvak.onlinestore.balance.domain;

public enum TransactionType {
  DEPOSIT("DEPOSIT"),
  WITHDRAW("WITHDRAW"),
  PURCHASE("PURCHASE"),
  REFUND("REFUND"),
  ADMIN_ADJUSTMENT("ADMIN_ADJUSTMENT");

  private final String value;

  TransactionType(String value) {
    this.value = value;
  }

  public static TransactionType fromString(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Transaction type cannot be null");
    }
    for (TransactionType type : values()) {
      if (type.value.equalsIgnoreCase(value.trim())) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid transaction type: " + value);
  }

  public String getValue() {
    return value;
  }

  public boolean isDeposit() {
    return this == DEPOSIT;
  }

  public boolean isWithdraw() {
    return this == WITHDRAW;
  }

  public boolean isPurchase() {
    return this == PURCHASE;
  }

  public boolean isRefund() {
    return this == REFUND;
  }

  public boolean isAdminAdjustment() {
    return this == ADMIN_ADJUSTMENT;
  }

  public boolean isDebit() {
    return this == WITHDRAW || this == PURCHASE;
  }

  public boolean isCredit() {
    return this == DEPOSIT || this == REFUND || this == ADMIN_ADJUSTMENT;
  }

  @Override
  public String toString() {
    return value;
  }
}
