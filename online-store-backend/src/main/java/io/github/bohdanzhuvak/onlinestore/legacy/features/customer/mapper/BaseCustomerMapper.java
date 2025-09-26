package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseRequestDto;
import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.legacy.common.mapper.BaseMapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Base mapper interface for customer functionality.
 * Provides common mapping operations for customer-facing DTOs.
 */
public interface BaseCustomerMapper<E extends BaseEntity, R extends BaseResponseDto, S extends BaseRequestDto> extends BaseMapper<E, R, S> {

  /**
   * Convert entity to response DTO
   */
  R toResponseDto(E entity);

  /**
   * Convert request DTO to entity
   */
  E toEntity(S dto);

  /**
   * Convert entity list to response DTO list
   */
  List<R> toResponseDtoList(List<E> entities);

  /**
   * Convert request DTO list to entity list
   */
  List<E> toEntityList(List<S> dtos);

  /**
   * Update entity from request DTO (ignores audit fields)
   */
  void updateEntity(@MappingTarget E entity, S dto);

  /**
   * Convert entity page to response DTO page
   */
  default Page<R> toResponseDtoPage(Page<E> entityPage) {
    return entityPage.map(this::toResponseDto);
  }
}
