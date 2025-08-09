package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  public List<OrderResponse> getOrders() {
    throw new UnsupportedOperationException();
  }

  public OrderResponse createOrder(Long userId, Long cartId) {
    throw new UnsupportedOperationException();
  }
}
