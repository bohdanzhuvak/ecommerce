package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

public record UpdateProductRequest(
    String name,
    String description,
    Money price,
    Integer stock,
    String categoryId
) {
  public record Money(Double amount, String currency) {
  }
}
