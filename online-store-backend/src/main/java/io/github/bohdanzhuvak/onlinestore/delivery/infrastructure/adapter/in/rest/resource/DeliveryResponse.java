package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for delivery response
 */
public record DeliveryResponse(
    @Schema(description = "Delivery ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "Order ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String orderId,

    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String userId,

    @Schema(description = "Delivery address", requiredMode = Schema.RequiredMode.REQUIRED)
    DeliveryAddressResponse address,

    @Schema(description = "Delivery status", requiredMode = Schema.RequiredMode.REQUIRED)
    String status,

    @Schema(description = "Tracking number", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    String trackingNumber,

    @Schema(description = "Additional notes", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    String notes,

    @Schema(description = "Creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime updatedAt
) {

  public static DeliveryResponse from(Delivery delivery) {
    return new DeliveryResponse(
        delivery.getId().getValue(),
        delivery.getOrderId().getValue(),
        delivery.getUserId().getValue(),
        DeliveryAddressResponse.from(delivery.getAddress()),
        delivery.getStatus().getValue(),
        delivery.getTrackingNumber() != null ? delivery.getTrackingNumber().getValue() : null,
        delivery.getNotes(),
        delivery.getCreatedAt(),
        delivery.getUpdatedAt()
    );
  }

  public static List<DeliveryResponse> from(List<Delivery> deliveries) {
    return deliveries.stream()
        .map(DeliveryResponse::from)
        .toList();
  }

  public record DeliveryAddressResponse(
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
      String recipientName,

      @Schema(description = "Phone number", requiredMode = Schema.RequiredMode.REQUIRED)
      String phoneNumber
  ) {

    public static DeliveryAddressResponse from(DeliveryAddress address) {
      return new DeliveryAddressResponse(
          address.street(),
          address.city(),
          address.state(),
          address.postalCode(),
          address.country(),
          address.recipientName(),
          address.phoneNumber()
      );
    }
  }
}
