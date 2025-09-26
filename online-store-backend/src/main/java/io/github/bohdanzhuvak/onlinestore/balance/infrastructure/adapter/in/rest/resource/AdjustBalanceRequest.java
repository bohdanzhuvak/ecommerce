package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource;

import java.math.BigDecimal;

public record AdjustBalanceRequest(
    String userId,
    BigDecimal amount,
    String currency,
    String description
) {
}
