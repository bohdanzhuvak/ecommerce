package io.github.bohdanzhuvak.onlinestore.legacy.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Base DTO for request operations (create/update).
 * Contains only fields that can be provided by client.
 */
@Getter
@Setter
public abstract class BaseRequestDto implements BaseDto {

  // Request DTOs typically don't have id, createdAt, updatedAt
  // as these are managed by the system
}
