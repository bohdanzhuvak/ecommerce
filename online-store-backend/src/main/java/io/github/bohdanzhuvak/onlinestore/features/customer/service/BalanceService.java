package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.BalanceResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.DepositRequest;

import java.math.BigDecimal;

public interface BalanceService {
  BalanceResponse getUserBalance(Long userId);

  void deposit(Long userId, DepositRequest request);

  void purchaseOrder(Long userId, Order order, BigDecimal amount);

  boolean hasSufficientFunds(Long userId, BigDecimal amount);
}
