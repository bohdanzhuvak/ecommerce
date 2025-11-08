package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.in.WebBalanceOrchestrator;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.AdjustBalanceRequest;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.BalanceResponse;
import io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.in.rest.resource.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "balance-admin", description = "Administrator balance and transaction management")
@RestController
@RequestMapping("/api/v1/admin/balance")
public class BalanceAdminController {
  private final WebBalanceOrchestrator webBalanceOrchestrator;

  public BalanceAdminController(WebBalanceOrchestrator webBalanceOrchestrator) {
    this.webBalanceOrchestrator = webBalanceOrchestrator;
  }

  @Operation(operationId = "adjustBalance", summary = "Adjust user balance", description = "Manually adjust user balance (admin only)")
  @PostMapping("/adjust")
  public ResponseEntity<BalanceResponse> adjustBalance(@RequestBody AdjustBalanceRequest request) {
    Balance balance = webBalanceOrchestrator.adjustBalance(
        UserId.of(request.userId()),
        Money.of(request.amount(), request.currency()),
        request.description()
    );
    return ResponseEntity.ok(BalanceResponse.from(balance));
  }

  @Operation(operationId = "getAllTransactions", summary = "Get all transactions", description = "Get all balance transactions with optional filters (admin only)")
  @GetMapping("/transactions")
  public ResponseEntity<List<TransactionResponse>> getAllTransactions(
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String type,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserId userIdObj = userId != null ? UserId.of(userId) : null;
      TransactionType typeObj = type != null ? TransactionType.fromString(type) : null;

      List<Transaction> transactions = webBalanceOrchestrator.getAllTransactions(
          userIdObj, typeObj, offset, limit);
      return ResponseEntity.ok(TransactionResponse.from(transactions));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
