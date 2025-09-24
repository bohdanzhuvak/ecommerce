package io.github.bohdanzhuvak.onlinestore.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.BalanceResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.DepositRequest;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.BalanceService;
import io.github.bohdanzhuvak.onlinestore.shared.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/balance")
@RequiredArgsConstructor
@Slf4j
public class BalanceController {

  private final BalanceService balanceService;

  @GetMapping
  public ResponseEntity<BalanceResponse> getUserBalance(@CurrentUser UserPrincipal user) {
    BalanceResponse response = balanceService.getUserBalance(user.id());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/deposit")
  public ResponseEntity<Void> deposit(@CurrentUser UserPrincipal user,
                                      @Valid @RequestBody DepositRequest request) {
    balanceService.deposit(user.id(), request);
    return ResponseEntity.ok().build();
  }
}
