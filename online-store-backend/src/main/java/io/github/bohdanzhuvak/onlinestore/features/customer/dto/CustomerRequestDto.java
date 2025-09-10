package io.github.bohdanzhuvak.onlinestore.features.customer.dto;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Base request DTO for customer functionality.
 * Contains only fields that can be provided by customer client.
 */
@Getter
@Setter
public abstract class CustomerRequestDto extends BaseRequestDto {

  // Customer request DTOs typically don't have audit fields
  // as these are managed by the system
}
