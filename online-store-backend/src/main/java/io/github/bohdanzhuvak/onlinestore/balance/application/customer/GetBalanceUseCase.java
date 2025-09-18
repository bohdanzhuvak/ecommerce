package io.github.bohdanzhuvak.onlinestore.balance.application.customer;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.stereotype.Service;

@Service
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
