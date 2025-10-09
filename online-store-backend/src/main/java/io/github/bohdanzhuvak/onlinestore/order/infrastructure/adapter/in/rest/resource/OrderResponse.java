package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for order response
 */
public record OrderResponse(
    String id,
    String userId,
    List<OrderItemResponse> items,
    MoneyResponse totalAmount,
    String status,
    DeliveryAddressResponse deliveryAddress,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static OrderResponse from(Order order) {
    return new OrderResponse(
        order.getId().getValue(),
        order.getUserId().getValue(),
        order.getItems().stream()
            .map(OrderItemResponse::from)
            .toList(),
        MoneyResponse.from(order.getTotalPrice()),
        order.getStatus().getValue(),
        DeliveryAddressResponse.from(order.getDeliveryAddressSnapshot()),
        order.getCreatedAt(),
        order.getUpdatedAt()
    );
  }

  public static List<OrderResponse> from(List<Order> orders) {
    return orders.stream()
        .map(OrderResponse::from)
        .toList();
  }

  public record OrderItemResponse(
      String productId,
      String productName,
      MoneyResponse productPrice,
      int quantity,
      MoneyResponse totalPrice
  ) {

    public static OrderItemResponse from(OrderItem item) {
      return new OrderItemResponse(
          item.productId().getValue(),
          item.productName(),
          MoneyResponse.from(item.unitPrice()),
          item.quantity(),
          MoneyResponse.from(item.getTotalPrice())
      );
    }
  }

  public record MoneyResponse(
      BigDecimal amount,
      String currency
  ) {

    public static MoneyResponse from(io.github.bohdanzhuvak.onlinestore.order.domain.Money money) {
      return new MoneyResponse(
          money.getAmount(),
          money.getCurrency()
      );
    }
  }

  public record DeliveryAddressResponse(
      String id,
      String street,
      String city,
      String state,
      String postalCode,
      String country,
      String recipientName
  ) {

    public static DeliveryAddressResponse from(io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressSnapshot address) {
      return new DeliveryAddressResponse(
          address.id().getValue(),
          address.street(),
          address.city(),
          address.state(),
          address.postalCode(),
          address.country(),
          address.recipientName()
      );
    }
  }
}
