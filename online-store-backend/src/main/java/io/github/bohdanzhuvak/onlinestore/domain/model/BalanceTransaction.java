package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "balance_transactions")
public class BalanceTransaction extends AuditableEntity {

  @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

  @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
    private TransactionType type;

  @NotNull(message = "Amount is required")
  @Digits(integer = 8, fraction = 2, message = "Amount must have at most 8 integer digits and 2 decimal places")
  @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

  @NotNull(message = "Balance after is required")
  @DecimalMin(value = "0.0", message = "Balance after must be non-negative")
  @Digits(integer = 8, fraction = 2, message = "Balance after must have at most 8 integer digits and 2 decimal places")
  @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balanceAfter;

  @Size(max = 500, message = "Description must not exceed 500 characters")
  @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
