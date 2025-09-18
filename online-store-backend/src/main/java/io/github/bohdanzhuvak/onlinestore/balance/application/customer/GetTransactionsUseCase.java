package io.github.bohdanzhuvak.onlinestore.balance.application.customer;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
