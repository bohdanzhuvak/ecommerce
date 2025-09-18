package io.github.bohdanzhuvak.onlinestore.order.application;

import io.github.bohdanzhuvak.onlinestore.order.application.admin.GetAllOrdersUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.admin.UpdateOrderStatusUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.customer.CancelOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.customer.CreateOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.customer.GetOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.customer.GetOrdersUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.customer.PayOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderApplicationService {
  private final CreateOrderUseCase createOrderUseCase;
  private final GetOrdersUseCase getOrdersUseCase;
  private final GetOrderUseCase getOrderUseCase;
  private final PayOrderUseCase payOrderUseCase;
  private final CancelOrderUseCase cancelOrderUseCase;
  private final GetAllOrdersUseCase getAllOrdersUseCase;
  private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

  public OrderApplicationService(CreateOrderUseCase createOrderUseCase,
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
  public Order createOrder(UserId userId, String deliveryAddressId) {
    CreateOrderUseCase.CreateOrderCommand command = new CreateOrderUseCase.CreateOrderCommand(
        userId, deliveryAddressId);
    return createOrderUseCase.execute(command);
  }

  public List<Order> getOrders(UserId userId) {
    return getOrdersUseCase.execute(userId);
  }

  public Optional<Order> getOrder(OrderId orderId, UserId userId) {
    return getOrderUseCase.execute(orderId, userId);
  }

  public Order payOrder(OrderId orderId, UserId userId) {
    PayOrderUseCase.PayOrderCommand command = new PayOrderUseCase.PayOrderCommand(
        orderId, userId);
    return payOrderUseCase.execute(command);
  }

  public Order cancelOrder(OrderId orderId, UserId userId) {
    CancelOrderUseCase.CancelOrderCommand command = new CancelOrderUseCase.CancelOrderCommand(
        orderId, userId);
    return cancelOrderUseCase.execute(command);
  }

  // Admin operations
  public List<Order> getAllOrders(OrderStatus status, int offset, int limit) {
    GetAllOrdersUseCase.GetAllOrdersCommand command = new GetAllOrdersUseCase.GetAllOrdersCommand(
        status, offset, limit);
    return getAllOrdersUseCase.execute(command);
  }

  public Order updateOrderStatus(OrderId orderId, OrderStatus newStatus) {
    UpdateOrderStatusUseCase.UpdateOrderStatusCommand command = new UpdateOrderStatusUseCase.UpdateOrderStatusCommand(
        orderId, newStatus);
    return updateOrderStatusUseCase.execute(command);
  }
}
