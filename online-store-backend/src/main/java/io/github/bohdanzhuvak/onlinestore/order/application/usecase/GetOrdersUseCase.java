package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;

@UseCase
public class GetOrdersUseCase {
  private final OrderRepository orderRepository;

  public GetOrdersUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<Order> execute(UserId userId) {
    return orderRepository.findByUserId(userId);
  }
}
