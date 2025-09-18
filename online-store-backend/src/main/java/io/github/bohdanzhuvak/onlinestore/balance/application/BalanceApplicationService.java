package io.github.bohdanzhuvak.onlinestore.balance.application;

import io.github.bohdanzhuvak.onlinestore.balance.application.admin.AdjustBalanceUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.admin.GetAllTransactionsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.customer.DepositUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.customer.GetBalanceUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.customer.GetTransactionsUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.application.customer.WithdrawUseCase;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BalanceApplicationService {
  private final GetBalanceUseCase getBalanceUseCase;
  private final DepositUseCase depositUseCase;
  private final WithdrawUseCase withdrawUseCase;
  private final GetTransactionsUseCase getTransactionsUseCase;
  private final AdjustBalanceUseCase adjustBalanceUseCase;
  private final GetAllTransactionsUseCase getAllTransactionsUseCase;

  public BalanceApplicationService(GetBalanceUseCase getBalanceUseCase,
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
  public Balance getBalance(UserId userId) {
    return getBalanceUseCase.execute(userId);
  }

  public Balance deposit(UserId userId, Money amount, String description) {
    DepositUseCase.DepositCommand command = new DepositUseCase.DepositCommand(
        userId, amount, description);
    return depositUseCase.execute(command);
  }

  public Balance withdraw(UserId userId, Money amount, String description) {
    WithdrawUseCase.WithdrawCommand command = new WithdrawUseCase.WithdrawCommand(
        userId, amount, description);
    return withdrawUseCase.execute(command);
  }

  public List<Transaction> getTransactions(UserId userId) {
    return getTransactionsUseCase.execute(userId);
  }

  public List<Transaction> getTransactions(UserId userId, int offset, int limit) {
    return getTransactionsUseCase.execute(userId, offset, limit);
  }

  // Admin operations
  public Balance adjustBalance(UserId userId, Money amount, String description) {
    AdjustBalanceUseCase.AdjustBalanceCommand command = new AdjustBalanceUseCase.AdjustBalanceCommand(
        userId, amount, description);
    return adjustBalanceUseCase.execute(command);
  }

  public List<Transaction> getAllTransactions(UserId userId, TransactionType type, int offset, int limit) {
    GetAllTransactionsUseCase.GetAllTransactionsCommand command = new GetAllTransactionsUseCase.GetAllTransactionsCommand(
        userId, type, offset, limit);
    return getAllTransactionsUseCase.execute(command);
  }
}
