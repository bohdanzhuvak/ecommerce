package io.github.bohdanzhuvak.onlinestore.domain.policy;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PayOrderPolicy implements OrderPolicy {

  private final BalanceChecker balanceChecker;

  @Override
  public String getType() {
    return "PAY";
  }

  @Override
  public void validate(Order order, Long userId) {
    if (!order.getUser().getId().equals(userId)) {
      throw new SecurityException("Access denied");
    }
    if (order.getStatus() != OrderStatus.PENDING) {
      throw new IllegalStateException("Order cannot be paid");
    }
    if (!balanceChecker.hasSufficientFunds(userId, order.getTotalPrice())) {
      throw new IllegalStateException("Insufficient funds");
    }
  }

  @Override
  public void apply(Order order) {
    order.setStatus(OrderStatus.PAID);
  }
}
