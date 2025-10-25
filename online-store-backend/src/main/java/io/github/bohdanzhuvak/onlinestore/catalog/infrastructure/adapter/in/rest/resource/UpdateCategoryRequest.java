package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

public record UpdateCategoryRequest(
    String name,
    String description
) {
}
