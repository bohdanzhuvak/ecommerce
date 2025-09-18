package io.github.bohdanzhuvak.onlinestore.order.application.customer;

import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
