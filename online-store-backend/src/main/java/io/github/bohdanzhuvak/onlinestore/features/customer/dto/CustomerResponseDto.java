package io.github.bohdanzhuvak.onlinestore.features.customer.dto;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base response DTO for customer functionality.
 * Contains only fields that can be returned to customer client.
 */
@Getter
@Setter
public abstract class CustomerResponseDto extends BaseResponseDto {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Customer response DTOs typically don't have admin metadata
  // as these are for customer use only
}
