package io.github.bohdanzhuvak.onlinestore.order.application.customer;

import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersUseCase {
  private final OrderRepository orderRepository;

  public GetOrdersUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<Order> execute(UserId userId) {
    return orderRepository.findByUserId(userId);
  }
}
