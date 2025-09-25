package io.github.bohdanzhuvak.onlinestore.order.application.ports.in;

import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface WebOrderOrchestrator {
  Order createOrder(UserId userId, DeliveryAddressId deliveryAddressId);

  List<Order> getOrders(UserId userId);

  Optional<Order> getOrder(OrderId orderId, UserId userId);

  Order payOrder(OrderId orderId, UserId userId);

  Order cancelOrder(OrderId orderId, UserId userId);

  List<Order> getAllOrders(OrderStatus status, int offset, int limit);

  Order updateOrderStatus(OrderId orderId, OrderStatus newStatus);
}
