package io.github.bohdanzhuvak.onlinestore.balance.api.customer;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.WebBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customer/balance")
public class BalanceController {
  private final WebBalanceOrchestrator webBalanceOrchestrator;

  public BalanceController(WebBalanceOrchestrator webBalanceOrchestrator) {
    this.webBalanceOrchestrator = webBalanceOrchestrator;
  }

  @GetMapping
  public ResponseEntity<Balance> getBalance(@RequestParam String userId) {
    Balance balance = webBalanceOrchestrator.getBalance(UserId.of(userId));
    return ResponseEntity.ok(balance);
  }

  @PostMapping("/deposit")
  public ResponseEntity<Balance> deposit(@RequestBody DepositRequest request) {
    try {
      Balance balance = webBalanceOrchestrator.deposit(
          UserId.of(request.getUserId()),
          Money.of(request.getAmount(), request.getCurrency()),
          request.getDescription()
      );
      return ResponseEntity.ok(balance);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/withdraw")
  public ResponseEntity<Balance> withdraw(@RequestBody WithdrawRequest request) {
    try {
      Balance balance = webBalanceOrchestrator.withdraw(
          UserId.of(request.getUserId()),
          Money.of(request.getAmount(), request.getCurrency()),
          request.getDescription()
      );
      return ResponseEntity.ok(balance);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<Transaction>> getTransactions(@RequestParam String userId,
                                                           @RequestParam(defaultValue = "0") int offset,
                                                           @RequestParam(defaultValue = "20") int limit) {
    List<Transaction> transactions = webBalanceOrchestrator.getTransactions(
        UserId.of(userId), offset, limit);
    return ResponseEntity.ok(transactions);
  }

  // DTO classes for requests
  public static class DepositRequest {
    private String userId;
    private BigDecimal amount;
    private String currency;
    private String description;

    // Getters and setters
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public BigDecimal getAmount() {
      return amount;
    }

    public void setAmount(BigDecimal amount) {
      this.amount = amount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }
  }

  public static class WithdrawRequest {
    private String userId;
    private BigDecimal amount;
    private String currency;
    private String description;

    // Getters and setters
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public BigDecimal getAmount() {
      return amount;
    }

    public void setAmount(BigDecimal amount) {
      this.amount = amount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }
  }
}
