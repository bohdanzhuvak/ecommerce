package io.github.bohdanzhuvak.onlinestore.balance.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

@UseCase
public class GetBalanceUseCase {
  private final BalanceRepository balanceRepository;

  public GetBalanceUseCase(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  public Balance execute(UserId userId) {
    return balanceRepository.findByUserId(userId)
        .orElse(Balance.of(userId, Money.zero("USD")));
  }
}
