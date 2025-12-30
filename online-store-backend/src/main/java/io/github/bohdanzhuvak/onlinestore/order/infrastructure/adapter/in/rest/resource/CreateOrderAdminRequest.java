package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource;

public record CreateOrderAdminRequest(
    String deliveryAddressId
) {
}
