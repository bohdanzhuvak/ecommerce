package io.github.bohdanzhuvak.onlinestore.common.mapper;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseRequestDto;
import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Minimal base mapper interface for common mapping operations.
 * This provides basic mapping functionality that can be extended by specific mappers.
 * Most mapping logic should be implemented in AdminBaseMapper or CustomerBaseMapper.
 */
public interface BaseMapper<E extends BaseEntity, R extends BaseResponseDto, S extends BaseRequestDto> {

  /**
   * Convert entity to DTO
   */
  R toResponseDto(E entity);

  /**
   * Convert DTO to entity
   */
  E toEntity(S dto);

  /**
   * Convert entity list to DTO list
   */
  List<R> toResponseDtoList(List<E> entities);

  /**
   * Convert DTO list to entity list
   */
  List<E> toEntityList(List<S> dtos);

  /**
   * Update entity from DTO (ignores audit fields)
   */
  void updateEntity(@MappingTarget E entity, S dto);

  /**
   * Convert entity page to DTO page
   */
  default Page<R> toResponseDtoPage(Page<E> entityPage) {
    return entityPage.map(this::toResponseDto);
  }
}
