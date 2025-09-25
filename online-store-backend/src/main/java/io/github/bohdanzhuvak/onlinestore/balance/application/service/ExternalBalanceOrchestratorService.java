package io.github.bohdanzhuvak.onlinestore.balance.application.service;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.ExternalBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.CreditBalanceForRefundUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.DebitBalanceForPurchaseUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.HasSufficientFundsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

public class ExternalBalanceOrchestratorService implements ExternalBalanceOrchestrator {
  private final HasSufficientFundsUseCase hasSufficientFundsUseCase;
  private final DebitBalanceForPurchaseUseCase debitBalanceForPurchaseUseCase;
  private final CreditBalanceForRefundUseCase creditBalanceForRefundUseCase;

  public ExternalBalanceOrchestratorService(HasSufficientFundsUseCase hasSufficientFundsUseCase,
                                            DebitBalanceForPurchaseUseCase debitBalanceForPurchaseUseCase,
                                            CreditBalanceForRefundUseCase creditBalanceForRefundUseCase) {
    this.hasSufficientFundsUseCase = hasSufficientFundsUseCase;
    this.debitBalanceForPurchaseUseCase = debitBalanceForPurchaseUseCase;
    this.creditBalanceForRefundUseCase = creditBalanceForRefundUseCase;
  }


  @Override
  public boolean hasSufficientFunds(UserId userId, Money amount) {
    HasSufficientFundsUseCase.HasSufficientFundsCommand command = new HasSufficientFundsUseCase.HasSufficientFundsCommand(
        userId,
        amount
    );
    return hasSufficientFundsUseCase.execute(command);
  }

  @Override
  public void debitBalanceForPurchase(UserId userId, Money amount, String description) {
    DebitBalanceForPurchaseUseCase.DebitBalanceForPurchaseCommand command = new DebitBalanceForPurchaseUseCase.DebitBalanceForPurchaseCommand(
        userId,
        amount,
        description
    );
    debitBalanceForPurchaseUseCase.execute(command);
  }

  @Override
  public void creditBalanceForRefund(UserId userId, Money amount, String description) {
    CreditBalanceForRefundUseCase.CreditBalanceForRefundCommand command = new CreditBalanceForRefundUseCase.CreditBalanceForRefundCommand(
        userId,
        amount,
        description
    );
    creditBalanceForRefundUseCase.execute(command);
  }
}
