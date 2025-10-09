package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for delivery response
 */
public record DeliveryResponse(
    String id,
    String orderId,
    String userId,
    DeliveryAddressResponse address,
    String status,
    String trackingNumber,
    String notes,
    LocalDateTime createdAt,
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
      String street,
      String city,
      String state,
      String postalCode,
      String country,
      String recipientName,
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
