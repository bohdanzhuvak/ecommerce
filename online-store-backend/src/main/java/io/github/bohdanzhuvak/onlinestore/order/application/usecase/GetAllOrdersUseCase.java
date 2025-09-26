package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;

import java.util.List;

@UseCase
public class GetAllOrdersUseCase {
  private final OrderRepository orderRepository;

  public GetAllOrdersUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<Order> execute(GetAllOrdersCommand command) {
    if (command.status() != null) {
      return orderRepository.findByStatus(command.status(), command.offset(), command.limit());
    } else {
      return orderRepository.findAll(command.offset(), command.limit());
    }
  }

  public record GetAllOrdersCommand(
      OrderStatus status,
      int offset,
      int limit
  ) {
  }
}
