package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.order.CreateOrderRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
  List<OrderResponse> getOrdersByUser(Long userId);

  OrderResponse getOrderById(Long orderId, Long userId);

  OrderResponse createOrder(Long userId, CreateOrderRequest request);

  OrderResponse payOrder(Long orderId, Long userId);

  OrderResponse cancelOrder(Long orderId, Long userId);
}
