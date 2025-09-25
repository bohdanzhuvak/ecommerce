package io.github.bohdanzhuvak.onlinestore.balance.application.usecase;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

public class WithdrawUseCase {
  private final BalanceRepository balanceRepository;
  private final TransactionRepository transactionRepository;

  public WithdrawUseCase(BalanceRepository balanceRepository, TransactionRepository transactionRepository) {
    this.balanceRepository = balanceRepository;
    this.transactionRepository = transactionRepository;
  }

  public Balance execute(WithdrawCommand command) {
    // Get current balance
    Balance currentBalance = balanceRepository.findByUserId(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("Balance not found for user"));

    // Check if user has sufficient funds
    if (!currentBalance.hasSufficientFunds(command.amount())) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    // Subtract amount from balance
    Balance newBalance = currentBalance.subtract(command.amount());

    // Save balance
    Balance savedBalance = balanceRepository.save(newBalance);

    // Create transaction
    Transaction transaction = Transaction.create(
        command.userId(),
        TransactionType.WITHDRAW,
        command.amount(),
        savedBalance.getAmount(),
        command.description()
    );
    transactionRepository.save(transaction);

    return savedBalance;
  }

  public record WithdrawCommand(
      UserId userId,
      Money amount,
      String description
  ) {
  }
}
