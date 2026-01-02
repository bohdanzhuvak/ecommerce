package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "DeliveryAddressResponseUserResponse")
public record DeliveryAddressResponse(
    @Schema(description = "Unique ID of the address", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "Street name", requiredMode = Schema.RequiredMode.REQUIRED)
    String street,

    @Schema(description = "City name", requiredMode = Schema.RequiredMode.REQUIRED)
    String city,

    @Schema(description = "State name", requiredMode = Schema.RequiredMode.REQUIRED)
    String state,

    @Schema(description = "Postal code", requiredMode = Schema.RequiredMode.REQUIRED)
    String postalCode,

    @Schema(description = "Country name", requiredMode = Schema.RequiredMode.REQUIRED)
    String country,

    @Schema(description = "Recipient full name", requiredMode = Schema.RequiredMode.REQUIRED)
    String recipientName,

    @Schema(description = "Is delivery address default", requiredMode = Schema.RequiredMode.REQUIRED)
    boolean isDefault
) {
  public static DeliveryAddressResponse from(DeliveryAddress deliveryAddress, boolean isDefault) {
    return new DeliveryAddressResponse(
        deliveryAddress.id().toString(),
        deliveryAddress.street(),
        deliveryAddress.city(),
        deliveryAddress.state(),
        deliveryAddress.postalCode(),
        deliveryAddress.country(),
        deliveryAddress.recipientName(),
        isDefault
    );
  }

  public static List<DeliveryAddressResponse> from(User user) {
    List<DeliveryAddress> deliveryAddresses = user.getAddresses();
    return deliveryAddresses.stream().map(deliveryAddress -> from(deliveryAddress, deliveryAddress.id().equals(user.getDefaultAddressId()))).toList();
  }
}
