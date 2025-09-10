package io.github.bohdanzhuvak.onlinestore.common.mapper;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseDto;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Base mapper interface for common mapping operations.
 * Concrete mappers should implement this interface using MapStruct.
 */
public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {

  /**
   * Convert entity to DTO
   */
  D toDto(E entity);

  /**
   * Convert DTO to entity
   */
  E toEntity(D dto);

  /**
   * Convert entity list to DTO list
   */
  List<D> toDtoList(List<E> entities);

  /**
   * Convert DTO list to entity list
   */
  List<E> toEntityList(List<D> dtos);

  /**
   * Convert entity page to DTO page
   */
  Page<D> toDtoPage(Page<E> entityPage);

  /**
   * Convert DTO to entity for creation (ignores audit fields)
   */
  E toEntityForCreate(D dto);

  /**
   * Update entity from DTO (ignores audit fields)
   */
  void updateEntity(E entity, D dto);
}
