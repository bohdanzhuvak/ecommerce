package io.github.bohdanzhuvak.onlinestore.architecture;

import java.util.List;

/**
 * Generic page result for paginated queries.
 * <p>
 * This is a framework-agnostic pagination result that can be used
 * across all modules in the application layer without depending on
 * Spring Data or any other infrastructure framework.
 * </p>
 *
 * @param <T> the type of elements in the page
 */
public record PageResult<T>(List<T> data, long total, int page, int pageSize) {

  public static <T> PageResult<T> of(List<T> data, long total, int page, int pageSize) {
    return new PageResult<>(data, total, page, pageSize);
  }

  public boolean hasNext() {
    return (long) (page + 1) * pageSize < total;
  }

  public boolean hasPrevious() {
    return page > 0;
  }

  public int getTotalPages() {
    return (int) Math.ceil((double) total / pageSize);
  }
}
