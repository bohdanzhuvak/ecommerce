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
public class DepositUseCase {
  private final BalanceRepository balanceRepository;
  private final TransactionRepository transactionRepository;

  public DepositUseCase(BalanceRepository balanceRepository, TransactionRepository transactionRepository) {
    this.balanceRepository = balanceRepository;
    this.transactionRepository = transactionRepository;
  }

  public Balance execute(DepositCommand command) {
    // Get current balance or create new one
    Balance currentBalance = balanceRepository.findByUserId(command.userId())
        .orElse(Balance.of(command.userId(), Money.zero(command.amount().getCurrency())));

    // Add amount to balance
    Balance newBalance = currentBalance.add(command.amount());

    // Save balance
    Balance savedBalance = balanceRepository.save(newBalance);

    // Create transaction
    Transaction transaction = Transaction.create(
        command.userId(),
        TransactionType.DEPOSIT,
        command.amount(),
        savedBalance.getAmount(),
        command.description()
    );
    transactionRepository.save(transaction);

    return savedBalance;
  }

  public record DepositCommand(
      UserId userId,
      Money amount,
      String description
  ) {
  }
}
