package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.WebBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.BalanceResponse;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.DepositRequest;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.TransactionResponse;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.WithdrawRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/balance")
public class BalanceController {
  private final WebBalanceOrchestrator webBalanceOrchestrator;

  public BalanceController(WebBalanceOrchestrator webBalanceOrchestrator) {
    this.webBalanceOrchestrator = webBalanceOrchestrator;
  }

  @GetMapping
  public ResponseEntity<BalanceResponse> getBalance(@CurrentUserId String userId) {
    Balance balance = webBalanceOrchestrator.getBalance(UserId.of(userId));
    return ResponseEntity.ok(BalanceResponse.from(balance));
  }

  @PostMapping("/deposit")
  public ResponseEntity<BalanceResponse> deposit(@CurrentUserId String userId, @RequestBody DepositRequest request) {
    try {
      Balance balance = webBalanceOrchestrator.deposit(
          UserId.of(userId),
          Money.of(request.amount(), request.currency()),
          request.description()
      );
      return ResponseEntity.ok(BalanceResponse.from(balance));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/withdraw")
  public ResponseEntity<BalanceResponse> withdraw(@CurrentUserId String userId, @RequestBody WithdrawRequest request) {
    try {
      Balance balance = webBalanceOrchestrator.withdraw(
          UserId.of(userId),
          Money.of(request.amount(), request.currency()),
          request.description()
      );
      return ResponseEntity.ok(BalanceResponse.from(balance));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<TransactionResponse>> getTransactions(@CurrentUserId String userId,
                                                                   @RequestParam(defaultValue = "0") int offset,
                                                                   @RequestParam(defaultValue = "20") int limit) {
    List<Transaction> transactions = webBalanceOrchestrator.getTransactions(
        UserId.of(userId), offset, limit);
    return ResponseEntity.ok(TransactionResponse.from(transactions));
  }
}
