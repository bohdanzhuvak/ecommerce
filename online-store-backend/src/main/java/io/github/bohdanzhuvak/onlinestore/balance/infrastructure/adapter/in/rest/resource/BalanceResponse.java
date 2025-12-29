package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for balance response
 */
public record BalanceResponse(
    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String userId,

    @Schema(description = "Current balance amount", requiredMode = Schema.RequiredMode.REQUIRED)
    MoneyResponse currentBalance,

    @Schema(description = "Last update timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
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
      @Schema(description = "Amount value", requiredMode = Schema.RequiredMode.REQUIRED)
      BigDecimal amount,

      @Schema(description = "Currency code", requiredMode = Schema.RequiredMode.REQUIRED)
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
