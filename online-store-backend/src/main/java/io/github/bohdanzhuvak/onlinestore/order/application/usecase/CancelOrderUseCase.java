package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

public class CancelOrderUseCase {
  private final OrderRepository orderRepository;

  public CancelOrderUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order execute(CancelOrderCommand command) {
    Order order = orderRepository.findById(command.orderId())
        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

    if (!order.belongsTo(command.userId())) {
      throw new IllegalArgumentException("Order does not belong to user");
    }

    order.cancel();

    return orderRepository.save(order);
  }

  public record CancelOrderCommand(
      OrderId orderId,
      UserId userId
  ) {
  }
}
