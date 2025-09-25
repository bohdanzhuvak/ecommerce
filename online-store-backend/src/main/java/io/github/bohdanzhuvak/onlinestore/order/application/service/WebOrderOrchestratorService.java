package io.github.bohdanzhuvak.onlinestore.order.application.service;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.in.WebOrderOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.CancelOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.CreateOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.GetAllOrdersUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.GetOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.GetOrdersUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.PayOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.UpdateOrderStatusUseCase;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;
import java.util.Optional;

public class WebOrderOrchestratorService implements WebOrderOrchestrator {
  private final CreateOrderUseCase createOrderUseCase;
  private final GetOrdersUseCase getOrdersUseCase;
  private final GetOrderUseCase getOrderUseCase;
  private final PayOrderUseCase payOrderUseCase;
  private final CancelOrderUseCase cancelOrderUseCase;
  private final GetAllOrdersUseCase getAllOrdersUseCase;
  private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

  public WebOrderOrchestratorService(CreateOrderUseCase createOrderUseCase,
                                     GetOrdersUseCase getOrdersUseCase,
                                     GetOrderUseCase getOrderUseCase,
                                     PayOrderUseCase payOrderUseCase,
                                     CancelOrderUseCase cancelOrderUseCase,
                                     GetAllOrdersUseCase getAllOrdersUseCase,
                                     UpdateOrderStatusUseCase updateOrderStatusUseCase) {
    this.createOrderUseCase = createOrderUseCase;
    this.getOrdersUseCase = getOrdersUseCase;
    this.getOrderUseCase = getOrderUseCase;
    this.payOrderUseCase = payOrderUseCase;
    this.cancelOrderUseCase = cancelOrderUseCase;
    this.getAllOrdersUseCase = getAllOrdersUseCase;
    this.updateOrderStatusUseCase = updateOrderStatusUseCase;
  }

  // Customer operations
  @Override
  public Order createOrder(UserId userId, String deliveryAddressId) {
    CreateOrderUseCase.CreateOrderCommand command = new CreateOrderUseCase.CreateOrderCommand(
        userId, deliveryAddressId);
    return createOrderUseCase.execute(command);
  }

  @Override
  public List<Order> getOrders(UserId userId) {
    return getOrdersUseCase.execute(userId);
  }

  @Override
  public Optional<Order> getOrder(OrderId orderId, UserId userId) {
    return getOrderUseCase.execute(orderId, userId);
  }

  @Override
  public Order payOrder(OrderId orderId, UserId userId) {
    PayOrderUseCase.PayOrderCommand command = new PayOrderUseCase.PayOrderCommand(
        orderId, userId);
    return payOrderUseCase.execute(command);
  }

  @Override
  public Order cancelOrder(OrderId orderId, UserId userId) {
    CancelOrderUseCase.CancelOrderCommand command = new CancelOrderUseCase.CancelOrderCommand(
        orderId, userId);
    return cancelOrderUseCase.execute(command);
  }

  // Admin operations
  @Override
  public List<Order> getAllOrders(OrderStatus status, int offset, int limit) {
    GetAllOrdersUseCase.GetAllOrdersCommand command = new GetAllOrdersUseCase.GetAllOrdersCommand(
        status, offset, limit);
    return getAllOrdersUseCase.execute(command);
  }

  @Override
  public Order updateOrderStatus(OrderId orderId, OrderStatus newStatus) {
    UpdateOrderStatusUseCase.UpdateOrderStatusCommand command = new UpdateOrderStatusUseCase.UpdateOrderStatusCommand(
        orderId, newStatus);
    return updateOrderStatusUseCase.execute(command);
  }
}
