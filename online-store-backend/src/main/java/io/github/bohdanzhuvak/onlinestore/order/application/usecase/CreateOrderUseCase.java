package io.github.bohdanzhuvak.onlinestore.order.application.usecase;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.CartPort;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.DeliveryPort;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderItem;
import io.github.bohdanzhuvak.onlinestore.order.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;

public class CreateOrderUseCase {
  private final OrderRepository orderRepository;
  private final CartPort cartPort;
  private final DeliveryPort deliveryPort;

  public CreateOrderUseCase(OrderRepository orderRepository,
                            CartPort cartPort,
                            DeliveryPort deliveryPort) {
    this.orderRepository = orderRepository;
    this.cartPort = cartPort;
    this.deliveryPort = deliveryPort;
  }

  public Order execute(CreateOrderCommand command) {
    // Validate delivery address exists and belongs to user
    if (!deliveryPort.existsAndBelongsToUser(command.deliveryAddressId(), command.userId().getValue())) {
      throw new IllegalArgumentException("Delivery address not found or does not belong to user");
    }

    // Get cart items
    List<CartPort.CartItem> cartItems = cartPort.getCartItems(command.userId().getValue());
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
        command.deliveryAddressId()
    );

    // Save order
    Order savedOrder = orderRepository.save(order);

    // Clear cart
    cartPort.clearCart(command.userId().getValue());

    return savedOrder;
  }

  private OrderItem toOrderItem(CartPort.CartItem cartItem) {
    return new OrderItem(
        ProductId.of(cartItem.productId()),
        cartItem.productName(),
        Money.of(cartItem.unitPriceAmount(), cartItem.unitPriceCurrency()),
        cartItem.quantity()
    );
  }

  public record CreateOrderCommand(
      UserId userId,
      String deliveryAddressId
  ) {
  }
}
