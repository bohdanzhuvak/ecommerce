package io.github.bohdanzhuvak.onlinestore.domain.factory;


import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;
import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.OrderItem;
import io.github.bohdanzhuvak.onlinestore.domain.model.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;

public class OrderFactory {

  private OrderFactory() {
  }

  public static Order create(User user, Cart cart, DeliveryAddress deliveryAddress) {
    Order order = Order.builder()
        .user(user)
        .status(OrderStatus.PENDING)
        .totalPrice(cart.getTotalPrice())
        .deliveryAddress(deliveryAddress)
        .build();

    cart.getItems().stream()
        .map(cartItem -> OrderItem.builder()
            .product(cartItem.getProduct())
            .quantity(cartItem.getQuantity())
            .pricePerUnit(cartItem.getProduct().getPrice())
            .build())
        .forEach(order::addItem);

    return order;
  }
}
