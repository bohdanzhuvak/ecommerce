package io.github.bohdanzhuvak.onlinestore.balance.application.admin;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllTransactionsUseCase {
  private final TransactionRepository transactionRepository;

  public GetAllTransactionsUseCase(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<Transaction> execute(GetAllTransactionsCommand command) {
    if (command.userId() != null && command.type() != null) {
      return transactionRepository.findByUserIdAndType(command.userId(), command.type());
    } else if (command.userId() != null) {
      return transactionRepository.findByUserId(command.userId(), command.offset(), command.limit());
    } else if (command.type() != null) {
      return transactionRepository.findByType(command.type());
    } else {
      return transactionRepository.findByUserId(command.userId(), command.offset(), command.limit());
    }
  }

  public record GetAllTransactionsCommand(
      UserId userId,
      TransactionType type,
      int offset,
      int limit
  ) {
  }
}
