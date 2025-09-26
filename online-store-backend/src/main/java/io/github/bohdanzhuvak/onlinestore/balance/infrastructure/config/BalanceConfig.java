package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.ExternalBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.WebBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.application.service.ExternalBalanceOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.balance.application.service.WebBalanceOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.AdjustBalanceUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.CreditBalanceForRefundUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.DebitBalanceForPurchaseUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.DepositUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.GetAllTransactionsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.GetBalanceUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.GetTransactionsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.HasSufficientFundsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.WithdrawUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceConfig {

  @Bean
  public WebBalanceOrchestrator webBalanceOrchestrator(GetBalanceUseCase getBalanceUseCase,
                                                       DepositUseCase depositUseCase,
                                                       WithdrawUseCase withdrawUseCase,
                                                       GetTransactionsUseCase getTransactionsUseCase,
                                                       AdjustBalanceUseCase adjustBalanceUseCase,
                                                       GetAllTransactionsUseCase getAllTransactionsUseCase) {
    return new WebBalanceOrchestratorService(getBalanceUseCase, depositUseCase, withdrawUseCase,
        getTransactionsUseCase, adjustBalanceUseCase, getAllTransactionsUseCase);
  }

  @Bean
  public ExternalBalanceOrchestrator externalBalanceOrchestrator(HasSufficientFundsUseCase hasSufficientFundsUseCase,
                                                                 DebitBalanceForPurchaseUseCase debitBalanceForPurchaseUseCase,
                                                                 CreditBalanceForRefundUseCase creditBalanceForRefundUseCase) {
    return new ExternalBalanceOrchestratorService(hasSufficientFundsUseCase, debitBalanceForPurchaseUseCase, creditBalanceForRefundUseCase);
  }
}
