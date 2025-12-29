package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record TransactionResponse(
    @Schema(description = "Transaction ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String userId,

    @Schema(description = "Transaction amount", requiredMode = Schema.RequiredMode.REQUIRED)
    BalanceResponse.MoneyResponse amount,

    @Schema(description = "Transaction type", requiredMode = Schema.RequiredMode.REQUIRED)
    String type,

    @Schema(description = "Transaction description", requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Transaction creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime createdAt
) {

  public static TransactionResponse from(Transaction transaction) {
    return new TransactionResponse(
        transaction.getId().getValue(),
        transaction.getUserId().getValue(),
        BalanceResponse.MoneyResponse.from(transaction.getAmount()),
        transaction.getType().getValue(),
        transaction.getDescription(),
        transaction.getCreatedAt()
    );
  }

  public static List<TransactionResponse> from(List<Transaction> transactions) {
    return transactions.stream()
        .map(TransactionResponse::from)
        .toList();
  }
}
