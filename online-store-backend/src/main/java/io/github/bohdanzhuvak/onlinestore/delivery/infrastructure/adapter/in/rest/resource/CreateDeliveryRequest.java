package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource;

public record CreateDeliveryRequest(
    String orderId,
    String street,
    String city,
    String state,
    String postalCode,
    String country,
    String recipientName,
    String phoneNumber
) {
}
