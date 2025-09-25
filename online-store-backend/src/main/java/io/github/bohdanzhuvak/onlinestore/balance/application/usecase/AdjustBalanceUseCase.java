package io.github.bohdanzhuvak.onlinestore.balance.application.usecase;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

public class AdjustBalanceUseCase {
  private final BalanceRepository balanceRepository;
  private final TransactionRepository transactionRepository;

  public AdjustBalanceUseCase(BalanceRepository balanceRepository, TransactionRepository transactionRepository) {
    this.balanceRepository = balanceRepository;
    this.transactionRepository = transactionRepository;
  }

  public Balance execute(AdjustBalanceCommand command) {
    // Get current balance or create new one
    Balance currentBalance = balanceRepository.findByUserId(command.userId())
        .orElse(Balance.of(command.userId(), Money.zero(command.amount().getCurrency())));

    // Adjust balance
    Balance newBalance;
    if (command.amount().getAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
      newBalance = currentBalance.add(command.amount());
    } else {
      newBalance = currentBalance.subtract(command.amount().negate());
    }

    // Save balance
    Balance savedBalance = balanceRepository.save(newBalance);

    // Create transaction
    Transaction transaction = Transaction.create(
        command.userId(),
        TransactionType.ADMIN_ADJUSTMENT,
        command.amount(),
        savedBalance.getAmount(),
        command.description()
    );
    transactionRepository.save(transaction);

    return savedBalance;
  }

  public record AdjustBalanceCommand(
      UserId userId,
      Money amount,
      String description
  ) {
  }
}
