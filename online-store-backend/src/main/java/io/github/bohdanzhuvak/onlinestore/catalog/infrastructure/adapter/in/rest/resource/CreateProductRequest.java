package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

import java.util.List;

public record CreateProductRequest(
    String name,
    String description,
    Double price,
    String currency,
    Integer stock,
    String categoryId,
    List<String> images
) {
}
