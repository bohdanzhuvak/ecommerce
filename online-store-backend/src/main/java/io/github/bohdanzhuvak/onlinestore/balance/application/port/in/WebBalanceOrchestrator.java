package io.github.bohdanzhuvak.onlinestore.balance.application.port.in;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;

import java.util.List;

public interface WebBalanceOrchestrator {
  Balance getBalance(UserId userId);

  Balance deposit(UserId userId, Money amount, String description);

  Balance withdraw(UserId userId, Money amount, String description);

  List<Transaction> getTransactions(UserId userId);

  List<Transaction> getTransactions(UserId userId, int offset, int limit);

  Balance adjustBalance(UserId userId, Money amount, String description);

  List<Transaction> getAllTransactions(UserId userId, TransactionType type, int offset, int limit);
}
