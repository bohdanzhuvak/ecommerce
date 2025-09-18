package io.github.bohdanzhuvak.onlinestore.order.application.customer;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.BalanceService;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class PayOrderUseCase {
  private final OrderRepository orderRepository;
  private final BalanceService balanceService;

  public PayOrderUseCase(OrderRepository orderRepository, BalanceService balanceService) {
    this.orderRepository = orderRepository;
    this.balanceService = balanceService;
  }

  public Order execute(PayOrderCommand command) {
    Order order = orderRepository.findById(command.orderId())
        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

    if (!order.belongsTo(command.userId())) {
      throw new IllegalArgumentException("Order does not belong to user");
    }

    // Check if user has sufficient balance
    if (!balanceService.hasSufficientFunds(command.userId().getValue(), order.getTotalPrice().getAmount())) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    // Deduct balance first
    balanceService.deductBalance(command.userId().getValue(), order.getTotalPrice().getAmount(),
        "Order payment: " + order.getId());

    // Pay order
    order.pay();
    Order savedOrder = orderRepository.save(order);

    return savedOrder;
  }

  public record PayOrderCommand(
      OrderId orderId,
      UserId userId
  ) {
  }
}
