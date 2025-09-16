package io.github.bohdanzhuvak.onlinestore.features.customer.service.impl;

import io.github.bohdanzhuvak.onlinestore.domain.model.BalanceTransaction;
import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.TransactionType;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BalanceTransactionRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.BalanceResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.BalanceTransactionResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance.DepositRequest;
import io.github.bohdanzhuvak.onlinestore.features.customer.mapper.BalanceMapper;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService {

  private final UserRepository userRepository;
  private final BalanceTransactionRepository balanceTransactionRepository;
  private final BalanceMapper balanceMapper;

  @Transactional(readOnly = true)
  @Override
  public BalanceResponse getUserBalance(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Page<BalanceTransaction> transactions = balanceTransactionRepository
        .findByUserIdOrderByCreatedAtDesc(userId, Pageable.ofSize(10));

    List<BalanceTransactionResponse> transactionDtos = transactions.getContent().stream()
        .map(balanceMapper::toResponse)
        .collect(Collectors.toList());

    return BalanceResponse.builder()
        .currentBalance(user.getBalance())
        .recentTransactions(transactionDtos)
        .build();
  }

  @Transactional
  @Override
  public void deposit(Long userId, DepositRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    BigDecimal newBalance = user.getBalance().add(request.getAmount());
    user.setBalance(newBalance);
    userRepository.save(user);

    createTransaction(user, request.getAmount(), newBalance, TransactionType.DEPOSIT,
        request.getDescription() != null ? request.getDescription() : "Deposit");

    log.info("User {} deposited {} to balance. New balance: {}",
        userId, request.getAmount(), newBalance);
  }

  @Transactional
  @Override
  public void purchaseOrder(Long userId, Order order, BigDecimal amount) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (user.getBalance().compareTo(amount) < 0) {
      throw new RuntimeException("Insufficient funds");
    }

    BigDecimal newBalance = user.getBalance().subtract(amount);
    user.setBalance(newBalance);
    userRepository.save(user);

    createTransaction(user, amount.negate(), newBalance, TransactionType.PURCHASE,
        "Order #" + order.getId(), order);

    log.info("User {} purchased order {} for {}. New balance: {}",
        userId, order.getId(), amount, newBalance);
  }

  @Transactional(readOnly = true)
  @Override
  public boolean hasSufficientFunds(Long userId, BigDecimal amount) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return user.getBalance().compareTo(amount) >= 0;
  }

  private void createTransaction(User user, BigDecimal amount, BigDecimal balanceAfter,
                                 TransactionType type, String description) {
    createTransaction(user, amount, balanceAfter, type, description, null);
  }

  private void createTransaction(User user, BigDecimal amount, BigDecimal balanceAfter,
                                 TransactionType type, String description, Order order) {
    BalanceTransaction transaction = BalanceTransaction.builder()
        .user(user)
        .type(type)
        .amount(amount)
        .balanceAfter(balanceAfter)
        .description(description)
        .order(order)
        .build();

    balanceTransactionRepository.save(transaction);
  }

}
