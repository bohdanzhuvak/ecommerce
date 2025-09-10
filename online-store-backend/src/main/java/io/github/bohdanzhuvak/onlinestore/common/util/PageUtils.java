package io.github.bohdanzhuvak.onlinestore.common.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public final class PageUtils {

  private PageUtils() {
    // Utility class
  }

  /**
   * Создать Pageable с сортировкой по умолчанию
   */
  public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
    return PageRequest.of(page, size, sort);
  }

  /**
   * Создать Pageable без сортировки
   */
  public static Pageable createPageable(int page, int size) {
    return PageRequest.of(page, size);
  }

  /**
   * Создать пустую страницу
   */
  public static <T> Page<T> createEmptyPage(Pageable pageable) {
    return new PageImpl<>(List.of(), pageable, 0);
  }

  /**
   * Проверить что страница пустая
   */
  public static boolean isEmpty(Page<?> page) {
    return page == null || page.getContent().isEmpty();
  }

  /**
   * Проверить что страница не пустая
   */
  public static boolean isNotEmpty(Page<?> page) {
    return !isEmpty(page);
  }
}
