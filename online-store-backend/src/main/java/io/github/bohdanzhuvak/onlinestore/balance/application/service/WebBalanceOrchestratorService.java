package io.github.bohdanzhuvak.onlinestore.balance.application.service;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.WebBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.AdjustBalanceUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.DepositUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.GetAllTransactionsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.GetBalanceUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.GetTransactionsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.usecase.WithdrawUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

import java.util.List;

public class WebBalanceOrchestratorService implements WebBalanceOrchestrator {
  private final GetBalanceUseCase getBalanceUseCase;
  private final DepositUseCase depositUseCase;
  private final WithdrawUseCase withdrawUseCase;
  private final GetTransactionsUseCase getTransactionsUseCase;
  private final AdjustBalanceUseCase adjustBalanceUseCase;
  private final GetAllTransactionsUseCase getAllTransactionsUseCase;

  public WebBalanceOrchestratorService(GetBalanceUseCase getBalanceUseCase,
                                       DepositUseCase depositUseCase,
                                       WithdrawUseCase withdrawUseCase,
                                       GetTransactionsUseCase getTransactionsUseCase,
                                       AdjustBalanceUseCase adjustBalanceUseCase,
                                       GetAllTransactionsUseCase getAllTransactionsUseCase) {
    this.getBalanceUseCase = getBalanceUseCase;
    this.depositUseCase = depositUseCase;
    this.withdrawUseCase = withdrawUseCase;
    this.getTransactionsUseCase = getTransactionsUseCase;
    this.adjustBalanceUseCase = adjustBalanceUseCase;
    this.getAllTransactionsUseCase = getAllTransactionsUseCase;
  }

  // Customer operations
  @Override
  public Balance getBalance(UserId userId) {
    return getBalanceUseCase.execute(userId);
  }

  @Override
  public Balance deposit(UserId userId, Money amount, String description) {
    DepositUseCase.DepositCommand command = new DepositUseCase.DepositCommand(
        userId, amount, description);
    return depositUseCase.execute(command);
  }

  @Override
  public Balance withdraw(UserId userId, Money amount, String description) {
    WithdrawUseCase.WithdrawCommand command = new WithdrawUseCase.WithdrawCommand(
        userId, amount, description);
    return withdrawUseCase.execute(command);
  }

  @Override
  public List<Transaction> getTransactions(UserId userId) {
    return getTransactionsUseCase.execute(userId);
  }

  @Override
  public List<Transaction> getTransactions(UserId userId, int offset, int limit) {
    return getTransactionsUseCase.execute(userId, offset, limit);
  }

  // Admin operations
  @Override
  public Balance adjustBalance(UserId userId, Money amount, String description) {
    AdjustBalanceUseCase.AdjustBalanceCommand command = new AdjustBalanceUseCase.AdjustBalanceCommand(
        userId, amount, description);
    return adjustBalanceUseCase.execute(command);
  }

  @Override
  public List<Transaction> getAllTransactions(UserId userId, TransactionType type, int offset, int limit) {
    GetAllTransactionsUseCase.GetAllTransactionsCommand command = new GetAllTransactionsUseCase.GetAllTransactionsCommand(
        userId, type, offset, limit);
    return getAllTransactionsUseCase.execute(command);
  }
}
