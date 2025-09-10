package io.github.bohdanzhuvak.onlinestore.features.customer.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
  private BigDecimal currentBalance;
  private List<BalanceTransactionResponse> recentTransactions;
}
