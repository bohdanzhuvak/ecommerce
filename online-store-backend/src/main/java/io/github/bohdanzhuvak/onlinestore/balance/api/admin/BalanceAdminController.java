package io.github.bohdanzhuvak.onlinestore.balance.api.admin;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.WebBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
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
@RequestMapping("/api/admin/balance")
public class BalanceAdminController {
  private final WebBalanceOrchestrator webBalanceOrchestrator;

  public BalanceAdminController(WebBalanceOrchestrator webBalanceOrchestrator) {
    this.webBalanceOrchestrator = webBalanceOrchestrator;
  }

  @PostMapping("/adjust")
  public ResponseEntity<Balance> adjustBalance(@RequestBody AdjustBalanceRequest request) {
    try {
      Balance balance = webBalanceOrchestrator.adjustBalance(
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
  public ResponseEntity<List<Transaction>> getAllTransactions(
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String type,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserId userIdObj = userId != null ? UserId.of(userId) : null;
      TransactionType typeObj = type != null ? TransactionType.fromString(type) : null;

      List<Transaction> transactions = webBalanceOrchestrator.getAllTransactions(
          userIdObj, typeObj, offset, limit);
      return ResponseEntity.ok(transactions);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // DTO classes for requests
  public static class AdjustBalanceRequest {
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
