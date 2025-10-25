package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CategoryResponse(
    String id,
    String name,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static CategoryResponse from(Category category) {
    return new CategoryResponse(
        category.getId().getValue(),
        category.getName(),
        category.getDescription(),
        category.getCreatedAt(),
        category.getUpdatedAt()
    );
  }

  public static List<CategoryResponse> from(List<Category> categories) {
    return categories.stream()
        .map(CategoryResponse::from)
        .collect(Collectors.toList());
  }
}
