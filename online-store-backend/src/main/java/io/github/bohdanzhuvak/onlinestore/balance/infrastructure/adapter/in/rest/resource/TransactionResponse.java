package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public record TransactionResponse(
    String id,
    String userId,
    BalanceResponse.MoneyResponse amount,
    String type,
    String description,
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
