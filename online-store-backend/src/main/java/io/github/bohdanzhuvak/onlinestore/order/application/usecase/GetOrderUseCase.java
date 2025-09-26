package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.Optional;

@UseCase
public class GetOrderUseCase {
  private final OrderRepository orderRepository;

  public GetOrderUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Optional<Order> execute(OrderId orderId, UserId userId) {
    return orderRepository.findById(orderId)
        .filter(order -> order.belongsTo(userId));
  }
}
