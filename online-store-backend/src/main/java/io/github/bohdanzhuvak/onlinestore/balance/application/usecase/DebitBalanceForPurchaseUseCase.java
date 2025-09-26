package io.github.bohdanzhuvak.onlinestore.balance.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

@UseCase
public class DebitBalanceForPurchaseUseCase {
  private final BalanceRepository balanceRepository;
  private final TransactionRepository transactionRepository;

  public DebitBalanceForPurchaseUseCase(BalanceRepository balanceRepository, TransactionRepository transactionRepository) {
    this.balanceRepository = balanceRepository;
    this.transactionRepository = transactionRepository;
  }

  public void execute(DebitBalanceForPurchaseCommand command) {
    Balance currentBalance = balanceRepository.findByUserId(command.userId()).orElseThrow(() -> new RuntimeException("Balance not found for user: " + command.userId()));
    Balance newBalance = currentBalance.subtract(command.amount());

    Balance savedBalance = balanceRepository.save(newBalance);

    Transaction transaction = Transaction.create(
        command.userId(),
        TransactionType.PURCHASE,
        command.amount(),
        savedBalance.getAmount(),
        command.description()
    );
    transactionRepository.save(transaction);
  }

  public record DebitBalanceForPurchaseCommand(
      UserId userId,
      Money amount,
      String description
  ) {
  }
}
