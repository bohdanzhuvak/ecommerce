package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
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

  public PageResult<Order> execute(GetAllOrdersCommand command) {
    // React-admin sends 1-based page numbers, convert to 0-based offset
    int offset = (command.page() - 1) * command.pageSize();
    List<Order> orders;
    long total;

    if (command.status() != null) {
      orders = orderRepository.findByStatus(command.status(), offset, command.pageSize());
      total = orderRepository.countByStatus(command.status());
    } else {
      orders = orderRepository.findAll(offset, command.pageSize());
      total = orderRepository.count();
    }

    return PageResult.of(orders, total, command.page(), command.pageSize());
  }

  public record GetAllOrdersCommand(
      OrderStatus status,
      int page,
      int pageSize
  ) {
  }
}
