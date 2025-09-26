package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource;

public record UpdateDeliveryStatusRequest(
    String status,
    String notes
) {
}
