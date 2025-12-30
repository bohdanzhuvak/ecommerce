package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for order response
 */
public record OrderCustomerResponse(
    @Schema(description = "Order ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "List of order items", requiredMode = Schema.RequiredMode.REQUIRED)
    List<OrderItemResponse> items,

    @Schema(description = "Total order price", requiredMode = Schema.RequiredMode.REQUIRED)
    MoneyResponse totalPrice,

    @Schema(description = "Order status", requiredMode = Schema.RequiredMode.REQUIRED)
    String status,

    @Schema(description = "Delivery address snapshot", requiredMode = Schema.RequiredMode.REQUIRED)
    DeliveryAddressResponse deliveryAddress,

    @Schema(description = "Creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime createdAt
) {

  public static OrderCustomerResponse from(Order order) {
    return new OrderCustomerResponse(
        order.getId().getValue(),
        order.getItems().stream()
            .map(OrderItemResponse::from)
            .toList(),
        MoneyResponse.from(order.getTotalPrice()),
        order.getStatus().getValue(),
        DeliveryAddressResponse.from(order.getDeliveryAddressSnapshot()),
        order.getCreatedAt()
    );
  }

  public static List<OrderCustomerResponse> from(List<Order> orders) {
    return orders.stream()
        .map(OrderCustomerResponse::from)
        .toList();
  }

  public record OrderItemResponse(
      @Schema(description = "Product ID", requiredMode = Schema.RequiredMode.REQUIRED)
      String productId,

      @Schema(description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED)
      String productName,

      @Schema(description = "Product unit price", requiredMode = Schema.RequiredMode.REQUIRED)
      MoneyResponse productPrice,

      @Schema(description = "Quantity ordered", requiredMode = Schema.RequiredMode.REQUIRED)
      int quantity,

      @Schema(description = "Total price for this item", requiredMode = Schema.RequiredMode.REQUIRED)
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
      @Schema(description = "Amount value", requiredMode = Schema.RequiredMode.REQUIRED)
      BigDecimal amount,

      @Schema(description = "Currency code", requiredMode = Schema.RequiredMode.REQUIRED)
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
      @Schema(description = "Address ID", requiredMode = Schema.RequiredMode.REQUIRED)
      String id,

      @Schema(description = "Street address", requiredMode = Schema.RequiredMode.REQUIRED)
      String street,

      @Schema(description = "City", requiredMode = Schema.RequiredMode.REQUIRED)
      String city,

      @Schema(description = "State", requiredMode = Schema.RequiredMode.REQUIRED)
      String state,

      @Schema(description = "Postal code", requiredMode = Schema.RequiredMode.REQUIRED)
      String postalCode,

      @Schema(description = "Country", requiredMode = Schema.RequiredMode.REQUIRED)
      String country,

      @Schema(description = "Recipient name", requiredMode = Schema.RequiredMode.REQUIRED)
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
