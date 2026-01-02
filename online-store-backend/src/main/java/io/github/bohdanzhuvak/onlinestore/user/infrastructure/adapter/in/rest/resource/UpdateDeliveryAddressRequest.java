package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource;

public record UpdateDeliveryAddressRequest(String id, String street, String city, String state, String postalCode,
                                           String country, String recipientName, boolean isDefault) {
}
