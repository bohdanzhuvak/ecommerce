package io.github.bohdanzhuvak.onlinestore.domain.policy;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.OrderStatus;

public class CancelOrderPolicy implements OrderPolicy {
  @Override
  public String getType() {
    return "CANCEL";
  }

  @Override
  public void validate(Order order, Long userId) {
    if (!order.getUser().getId().equals(userId)) {
      throw new SecurityException("Access denied");
    }
    if (order.getStatus() != OrderStatus.PENDING) {
      throw new IllegalStateException("Only pending orders can be cancelled");
    }
  }

  @Override
  public void apply(Order order) {
    order.setStatus(OrderStatus.CANCELLED);
  }
}
