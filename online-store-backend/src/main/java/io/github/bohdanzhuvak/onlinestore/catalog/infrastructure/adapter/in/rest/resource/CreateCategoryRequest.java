package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

public record CreateCategoryRequest(
    String name,
    String description
) {
}
