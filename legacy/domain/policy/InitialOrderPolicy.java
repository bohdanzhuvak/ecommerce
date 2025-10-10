package io.github.bohdanzhuvak.onlinestore.domain.policy;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.OrderStatus;

public class InitialOrderPolicy implements OrderPolicy {
  @Override
  public String getType() {
    return "INITIAL";
  }

  @Override
  public void validate(Order order, Long userId) {
    if (order.getItems().isEmpty()) {
      throw new IllegalStateException("Cannot create order with empty cart");
    }
  }

  @Override
  public void apply(Order order) {
    order.setStatus(OrderStatus.PENDING);
  }
}
