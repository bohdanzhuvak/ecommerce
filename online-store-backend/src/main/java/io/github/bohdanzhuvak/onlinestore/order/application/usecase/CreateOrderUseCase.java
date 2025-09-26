package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.CartPort;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.UserPort;
import io.github.bohdanzhuvak.onlinestore.order.domain.CartItemSnapshot;
import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressSnapshot;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderItem;
import io.github.bohdanzhuvak.onlinestore.order.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;

@UseCase
public class CreateOrderUseCase {
  private final OrderRepository orderRepository;
  private final CartPort cartPort;
  private final UserPort userPort;

  public CreateOrderUseCase(OrderRepository orderRepository,
                            CartPort cartPort,
                            UserPort userPort) {
    this.orderRepository = orderRepository;
    this.cartPort = cartPort;
    this.userPort = userPort;
  }

  public Order execute(CreateOrderCommand command) {
    // Validate delivery address exists and belongs to user
    DeliveryAddressSnapshot deliveryAddressSnapshot = userPort.getDeliveryAddressById(command.userId(), command.deliveryAddressId());

    // Get cart items
    List<CartItemSnapshot> cartItems = cartPort.getCartItems(command.userId());
    if (cartItems.isEmpty()) {
      throw new IllegalArgumentException("Cart is empty");
    }

    // Convert cart items to order items
    List<OrderItem> orderItems = cartItems.stream()
        .map(this::toOrderItem)
        .toList();

    // Create order
    Order order = new Order(
        OrderId.generate(),
        command.userId(),
        orderItems,
        deliveryAddressSnapshot
    );

    // Save order
    Order savedOrder = orderRepository.save(order);

    // Clear cart
    cartPort.clearCart(command.userId());

    return savedOrder;
  }

  private OrderItem toOrderItem(CartItemSnapshot cartItem) {
    return new OrderItem(
        ProductId.of(cartItem.getProductId()),
        cartItem.getProductName(),
        cartItem.getPricePerUnit(),
        cartItem.getQuantity()
    );
  }

  public record CreateOrderCommand(
      UserId userId,
      DeliveryAddressId deliveryAddressId
  ) {
  }
}
