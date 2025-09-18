package io.github.bohdanzhuvak.onlinestore.order.application.admin;

import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStatusUseCase {
  private final OrderRepository orderRepository;

  public UpdateOrderStatusUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order execute(UpdateOrderStatusCommand command) {
    Order order = orderRepository.findById(command.orderId())
        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

    // Update status based on the new status
    switch (command.newStatus()) {
      case PAID -> order.pay();
      case CANCELLED -> order.cancel();
      case SHIPPED -> order.ship();
      case DELIVERED -> order.deliver();
      default -> throw new IllegalArgumentException("Invalid status transition: " + command.newStatus());
    }

    return orderRepository.save(order);
  }

  public record UpdateOrderStatusCommand(
      OrderId orderId,
      OrderStatus newStatus
  ) {
  }
}
