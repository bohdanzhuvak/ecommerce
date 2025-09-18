package io.github.bohdanzhuvak.onlinestore.order.application.customer;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.CartService;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.DeliveryAddressService;
import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderItem;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateOrderUseCase {
  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final DeliveryAddressService deliveryAddressService;

  public CreateOrderUseCase(OrderRepository orderRepository,
                            CartService cartService,
                            DeliveryAddressService deliveryAddressService) {
    this.orderRepository = orderRepository;
    this.cartService = cartService;
    this.deliveryAddressService = deliveryAddressService;
  }

  public Order execute(CreateOrderCommand command) {
    // Validate delivery address exists and belongs to user
    if (!deliveryAddressService.existsAndBelongsToUser(command.deliveryAddressId(), command.userId().getValue())) {
      throw new IllegalArgumentException("Delivery address not found or does not belong to user");
    }

    // Get cart items
    List<CartService.CartItem> cartItems = cartService.getCartItems(command.userId().getValue());
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
    cartService.clearCart(command.userId().getValue());

    return savedOrder;
  }

  private OrderItem toOrderItem(CartService.CartItem cartItem) {
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
