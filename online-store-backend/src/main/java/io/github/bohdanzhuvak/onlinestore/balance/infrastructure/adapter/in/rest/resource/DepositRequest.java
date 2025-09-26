package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource;

import java.math.BigDecimal;

public record DepositRequest(
    BigDecimal amount,
    String currency,
    String description
) {
}
