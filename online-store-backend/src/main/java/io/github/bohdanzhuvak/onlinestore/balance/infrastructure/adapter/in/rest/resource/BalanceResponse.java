package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for balance response
 */
public record BalanceResponse(
    String userId,
    MoneyResponse currentBalance,
    LocalDateTime lastUpdated
) {

  public static BalanceResponse from(Balance balance) {
    return new BalanceResponse(
        balance.getUserId().getValue(),
        MoneyResponse.from(balance.getAmount()),
        balance.getLastUpdated()
    );
  }

  public record MoneyResponse(
      BigDecimal amount,
      String currency
  ) {

    public static MoneyResponse from(io.github.bohdanzhuvak.onlinestore.balance.domain.Money money) {
      return new MoneyResponse(
          money.getAmount(),
          money.getCurrency()
      );
    }
  }
}
