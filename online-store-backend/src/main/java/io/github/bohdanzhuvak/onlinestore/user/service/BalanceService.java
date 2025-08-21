package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.model.BalanceTransaction;
import io.github.bohdanzhuvak.onlinestore.common.model.Order;
import io.github.bohdanzhuvak.onlinestore.common.model.TransactionType;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.BalanceTransactionRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.balance.BalanceResponse;
import io.github.bohdanzhuvak.onlinestore.user.dto.balance.BalanceTransactionDto;
import io.github.bohdanzhuvak.onlinestore.user.dto.balance.DepositRequest;
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
public class BalanceService {
    
    private final UserRepository userRepository;
    private final BalanceTransactionRepository balanceTransactionRepository;
    
    @Transactional(readOnly = true)
    public BalanceResponse getUserBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Page<BalanceTransaction> transactions = balanceTransactionRepository
                .findByUserIdOrderByCreatedAtDesc(userId, Pageable.ofSize(10));
        
        List<BalanceTransactionDto> transactionDtos = transactions.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        
        return BalanceResponse.builder()
                .currentBalance(user.getBalance())
                .recentTransactions(transactionDtos)
                .build();
    }
    
    @Transactional
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
    
    private BalanceTransactionDto mapToDto(BalanceTransaction transaction) {
        return BalanceTransactionDto.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .balanceAfter(transaction.getBalanceAfter())
                .description(transaction.getDescription())
                .orderId(transaction.getOrder() != null ? transaction.getOrder().getId() : null)
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
