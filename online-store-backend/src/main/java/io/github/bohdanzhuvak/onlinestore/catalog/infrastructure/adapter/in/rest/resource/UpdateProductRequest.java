package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

public record UpdateProductRequest(
    String name,
    String description,
    Double price,
    String currency,
    Integer stock,
    String categoryId
) {
}
