package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CategoryResponse(
    @Schema(description = "Category ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "Category name", requiredMode = Schema.RequiredMode.REQUIRED)
    String name,

    @Schema(description = "Category description", requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
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
