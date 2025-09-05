package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.OrderAlreadyCancelledException;
import io.github.bohdanzhuvak.onlinestore.common.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.common.model.Order;
import io.github.bohdanzhuvak.onlinestore.common.model.OrderItem;
import io.github.bohdanzhuvak.onlinestore.common.model.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.CartRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.DeliveryAddressRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.order.CreateOrderRequest;
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
  private final DeliveryAddressRepository deliveryAddressRepository;
  private final BalanceService balanceService;

  public List<OrderResponse> getOrdersByUser(Long userId) {
    List<Order> orders = orderRepository.findAllByUser_Id(userId);
    return orderMapper.toResponse(orders);
  }

  public OrderResponse getOrderById(Long orderId, Long userId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

    if (!order.getUser().getId().equals(userId)) {
      throw new RuntimeException("Access denied to order");
    }

    return orderMapper.toResponse(order);
  }

  @Transactional
  public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    Cart cart = cartRepository.findByUserId(userId)
        .orElseThrow(() -> new NotFoundException("Cart not found for user with id: " + userId));

    DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(request.getDeliveryAddressId())
        .orElseThrow(() -> new NotFoundException("Delivery address not found"));

    if (!deliveryAddress.getUser().getId().equals(userId)) {
      throw new RuntimeException("Access denied to delivery address");
    }

    Order order = Order.builder()
        .user(user)
        .status(OrderStatus.PENDING)
        .totalPrice(cart.getTotalPrice())
        .deliveryAddress(deliveryAddress)
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
  public OrderResponse payOrder(Long orderId, Long userId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

    if (order.getStatus() == OrderStatus.PAID) {
      throw new RuntimeException("Order is already paid");
    }

    if (order.getStatus() == OrderStatus.CANCELLED) {
      throw new RuntimeException("Cannot pay cancelled order");
    }

    if (!order.getUser().getId().equals(userId)) {
      throw new RuntimeException("Access denied to order");
    }

    if (!balanceService.hasSufficientFunds(userId, order.getTotalPrice())) {
      throw new RuntimeException("Insufficient funds to pay for this order");
    }

    balanceService.purchaseOrder(userId, order, order.getTotalPrice());

    order.setStatus(OrderStatus.PAID);
    order = orderRepository.save(order);

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
