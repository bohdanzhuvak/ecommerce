package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.BalancePort;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

public class PayOrderUseCase {
  private final OrderRepository orderRepository;
  private final BalancePort balancePort;

  public PayOrderUseCase(OrderRepository orderRepository, BalancePort balancePort) {
    this.orderRepository = orderRepository;
    this.balancePort = balancePort;
  }

  public Order execute(PayOrderCommand command) {
    Order order = orderRepository.findById(command.orderId())
        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

    if (!order.belongsTo(command.userId())) {
      throw new IllegalArgumentException("Order does not belong to user");
    }

    order.pay();

    balancePort.debitBalanceForPurchase(command.userId(), order.getTotalPrice(),
        "Order payment: " + order.getId());

    return orderRepository.save(order);
  }

  public record PayOrderCommand(
      OrderId orderId,
      UserId userId
  ) {
  }
}
