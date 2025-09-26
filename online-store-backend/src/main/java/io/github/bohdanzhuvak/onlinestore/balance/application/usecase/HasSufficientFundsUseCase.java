package io.github.bohdanzhuvak.onlinestore.balance.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

@UseCase
public class HasSufficientFundsUseCase {
  private final BalanceRepository balanceRepository;

  public HasSufficientFundsUseCase(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  public boolean execute(HasSufficientFundsCommand command) {
    Balance balance = balanceRepository.findByUserId(command.userId()).orElseThrow(() -> new RuntimeException("Balance not found for user: " + command.userId()));
    return balance.hasSufficientFunds(command.amount());
  }

  public record HasSufficientFundsCommand(
      UserId userId,
      Money amount
  ) {
  }
}
