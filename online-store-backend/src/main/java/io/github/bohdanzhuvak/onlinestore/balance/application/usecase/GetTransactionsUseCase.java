package io.github.bohdanzhuvak.onlinestore.balance.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

import java.util.List;

@UseCase
public class GetTransactionsUseCase {
  private final TransactionRepository transactionRepository;

  public GetTransactionsUseCase(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<Transaction> execute(UserId userId) {
    return transactionRepository.findByUserId(userId);
  }

  public List<Transaction> execute(UserId userId, int offset, int limit) {
    return transactionRepository.findByUserId(userId, offset, limit);
  }
}
