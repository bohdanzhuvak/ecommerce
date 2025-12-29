package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "DeliveryAddressResponseUserResponse")
public record DeliveryAddressResponse(
    @Schema(description = "Unique ID of the address")
    @NotBlank
    String id,

    @Schema(description = "Street name")
    @NotBlank
    String street,

    @Schema(description = "City name")
    @NotBlank
    String city,

    @Schema(description = "State name")
    @NotBlank
    String state,

    @Schema(description = "Postal code")
    @NotBlank
    String postalCode,

    @Schema(description = "Country name")
    @NotBlank
    String country,

    @Schema(description = "Recipient full name")
    @NotBlank
    String recipientName,

    @Schema(description = "Is delivery address default")
    @NotBlank
    boolean isDefault
) {
  private static DeliveryAddressResponse from(DeliveryAddress deliveryAddress, boolean isDefault) {
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
    return deliveryAddresses.stream().map(deliveryAddress -> from(deliveryAddress, deliveryAddress.id() == user.getDefaultAddressId())).toList();
  }
}
