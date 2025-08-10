package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.OrderAlreadyCancelledException;
import io.github.bohdanzhuvak.onlinestore.common.model.Order;
import io.github.bohdanzhuvak.onlinestore.common.model.OrderItem;
import io.github.bohdanzhuvak.onlinestore.common.model.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.CartRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.user.mapper.OrderMapper;
import io.github.bohdanzhuvak.onlinestore.user.model.Cart;
import io.github.bohdanzhuvak.onlinestore.user.model.CartItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final UserRepository userRepository;

  public List<OrderResponse> getOrders() {
    List<Order> orders = orderRepository.findAll();
    return orderMapper.toResponse(orders);
  }

  @Transactional
  public OrderResponse createOrder(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    Cart cart = cartRepository.findByUserId(userId)
        .orElseThrow(() -> new NotFoundException("Cart not found for user with id: " + userId));

    Order order = Order.builder()
        .user(user)
        .status(OrderStatus.PENDING)
        .totalPrice(cart.getTotalPrice())
        .build();

    for (CartItem cartItem : cart.getItems()) {
      OrderItem orderItem = OrderItem.builder()
          .product(cartItem.getProduct())
          .quantity(cartItem.getQuantity())
          .pricePerUnit(cartItem.getProduct().getPrice())
          .build();

      order.addItem(orderItem);
    }


    order = orderRepository.save(order);

    cart.getItems().clear();
    cartRepository.save(cart);

    return orderMapper.toResponse(order);
  }

  @Transactional
  public OrderResponse cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

    if (order.getStatus() == OrderStatus.CANCELLED) {
      throw new OrderAlreadyCancelledException("Order is already cancelled");
    }

    order.setStatus(OrderStatus.CANCELLED);
    order = orderRepository.save(order);

    return orderMapper.toResponse(order);
  }
}
