package io.github.bohdanzhuvak.onlinestore.order.application.customer;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.BalanceService;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderUseCase {
  private final OrderRepository orderRepository;
  private final BalanceService balanceService;

  public CancelOrderUseCase(OrderRepository orderRepository, BalanceService balanceService) {
    this.orderRepository = orderRepository;
    this.balanceService = balanceService;
  }

  public Order execute(CancelOrderCommand command) {
    Order order = orderRepository.findById(command.orderId())
        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

    if (!order.belongsTo(command.userId())) {
      throw new IllegalArgumentException("Order does not belong to user");
    }

    // Refund if order was paid
    if (order.getStatus().isPaid()) {
      balanceService.addBalance(command.userId().getValue(), order.getTotalPrice().getAmount(),
          "Order cancellation refund: " + order.getId());
    }

    // Cancel order
    order.cancel();
    Order savedOrder = orderRepository.save(order);

    return savedOrder;
  }

  public record CancelOrderCommand(
      OrderId orderId,
      UserId userId
  ) {
  }
}
