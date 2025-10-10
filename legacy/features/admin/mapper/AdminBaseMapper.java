package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.legacy.common.mapper.BaseMapper;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminResponseDto;

/**
 * Base mapper interface for admin functionality.
 * Handles mapping between entities and admin DTOs (request/response).
 */
public interface AdminBaseMapper<E extends BaseEntity, RE extends AdminResponseDto, RT extends AdminRequestDto> extends BaseMapper<E, RE, RT> {
  /*
   *//**
   * Convert entity to DTO with admin metadata
   *
   * @param entity Entity to convert
   * @return DTO with admin metadata
   *//*
  default RE toAdminDto(E entity) {
    RE dto = toResponseDto(entity);
    if (dto != null) {
      // Set default admin metadata
      dto.setEditable(true);
      dto.setDeletable(true);
      dto.setDisplayName(generateDisplayName(entity));
    }
    return dto;
  }

  *//**
   * Generate display name for entity
   * Can be overridden in concrete mappers
   *
   * @param entity Entity to generate display name for
   * @return Display name
   *//*
  default String generateDisplayName(E entity) {
    return entity.getClass().getSimpleName() + " #" + entity.getId();
  }

  *//**
   * Convert entity page to admin DTO page with metadata
   *
   * @param entityPage Page of entities
   * @return Page of admin DTOs
   *//*
  default Page<RE> toAdminDtoPage(Page<E> entityPage) {
    return entityPage.map(this::toAdminDto);
  }

  *//**
   * Convert entity list to admin DTO list with metadata
   *
   * @param entities List of entities
   * @return List of admin DTOs
   *//*
  default List<RE> toAdminDtoList(List<E> entities) {
    return entities.stream()
        .map(this::toAdminDto)
        .toList();
  }*/
}
