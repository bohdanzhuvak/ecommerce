package io.github.bohdanzhuvak.onlinestore.legacy.common.util;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EntityUtils {

  private EntityUtils() {
    // Utility class
  }

  /**
   * Извлечь ID из сущности
   */
  public static Long extractId(BaseEntity entity) {
    return entity != null ? entity.getId() : null;
  }

  /**
   * Извлечь ID из коллекции сущностей
   */
  public static List<Long> extractIds(Collection<? extends BaseEntity> entities) {
    if (CollectionUtils.isEmpty(entities)) {
      return List.of();
    }
    return entities.stream()
        .map(EntityUtils::extractId)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  /**
   * Проверить что сущность имеет ID
   */
  public static boolean hasId(BaseEntity entity) {
    return entity != null && entity.getId() != null;
  }

  /**
   * Проверить что сущность не имеет ID (новая)
   */
  public static boolean isNew(BaseEntity entity) {
    return entity != null && entity.getId() == null;
  }

  /**
   * Проверить что сущность существует (имеет ID)
   */
  public static boolean isPersisted(BaseEntity entity) {
    return hasId(entity);
  }
}
