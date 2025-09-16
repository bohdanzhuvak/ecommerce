package io.github.bohdanzhuvak.onlinestore.features.customer.service.impl;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.domain.factory.OrderFactory;
import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;
import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.policy.OrderPolicy;
import io.github.bohdanzhuvak.onlinestore.domain.policy.OrderPolicyRegistry;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CartRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.DeliveryAddressRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.order.CreateOrderRequest;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.mapper.OrderMapper;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.BalanceService;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final UserRepository userRepository;
  private final DeliveryAddressRepository deliveryAddressRepository;
  private final BalanceService balanceService;
  private final OrderPolicyRegistry policyRegistry;

  @Override
  public List<OrderResponse> getOrdersByUser(Long userId) {
    List<Order> orders = orderRepository.findAllByUser_Id(userId);
    return orderMapper.toResponse(orders);
  }

  @Override
  public OrderResponse getOrderById(Long orderId, Long userId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

    if (!order.getUser().getId().equals(userId)) {
      throw new RuntimeException("Access denied to order");
    }

    return orderMapper.toResponse(order);
  }


  @Transactional
  @Override
  public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
    User user = findUserById(userId);
    Cart cart = findCartByUserId(userId);
    DeliveryAddress address = findDeliveryAddress(request.getDeliveryAddressId(), userId);

    Order order = OrderFactory.create(user, cart, address);
    executePolicy("INITIAL", order, userId);
    order = orderRepository.save(order);

    cart.clear();
    cartRepository.save(cart);

    return orderMapper.toResponse(order);
  }

  @Transactional
  @Override
  public OrderResponse payOrder(Long orderId, Long userId) {
    Order order = findOrderById(orderId);

    executePolicy("PAY", order, userId);
    order = orderRepository.save(order);

    balanceService.purchaseOrder(userId, order, order.getTotalPrice());

    return orderMapper.toResponse(order);
  }

  @Transactional
  @Override
  public OrderResponse cancelOrder(Long orderId, Long userId) {
    Order order = findOrderById(orderId);

    executePolicy("CANCEL", order, userId);
    order = orderRepository.save(order);

    return orderMapper.toResponse(order);
  }

  private void executePolicy(String policyType, Order order, Long userId) {
    OrderPolicy policy = policyRegistry.getPolicy(policyType);
    policy.validate(order, userId);
    policy.apply(order);
  }


  //Helpers
  private Order findOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));
  }

  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));
  }

  private Cart findCartByUserId(Long userId) {
    return cartRepository.findByUserId(userId)
        .orElseThrow(() -> new NotFoundException("Cart not found for user with id: " + userId));
  }

  private DeliveryAddress findDeliveryAddress(Long addressId, Long userId) {
    DeliveryAddress address = deliveryAddressRepository.findById(addressId)
        .orElseThrow(() -> new NotFoundException("Delivery address not found"));

    if (!address.getUser().getId().equals(userId)) {
      throw new RuntimeException("Access denied to delivery address");
    }
    return address;
  }
}
