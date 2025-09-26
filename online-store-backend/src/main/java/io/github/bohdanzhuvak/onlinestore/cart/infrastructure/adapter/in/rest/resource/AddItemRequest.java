package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource;

public record AddItemRequest(
    String productId,
    Integer quantity
) {
}
