package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Generic paged response for REST API endpoints.
 * Compatible with react-admin data provider format.
 */
public record PagedResponse<T>(
    List<T> data,
    long total
) {
  public static <T> PagedResponse<T> from(PageResult<T> pageResult) {
    return new PagedResponse<>(pageResult.data(), pageResult.total());
  }

  public static <T, R> PagedResponse<R> from(PageResult<T> pageResult, Function<T, R> mapper) {
    List<R> mappedData = pageResult.data().stream()
        .map(mapper)
        .collect(Collectors.toList());
    return new PagedResponse<>(mappedData, pageResult.total());
  }
}
