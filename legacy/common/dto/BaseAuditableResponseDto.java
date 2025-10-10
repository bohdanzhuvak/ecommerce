package io.github.bohdanzhuvak.onlinestore.legacy.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base DTO for auditable response operations (read operations).
 * Contains all fields that can be returned to client including audit fields.
 */
@Getter
@Setter
public abstract class BaseAuditableResponseDto extends BaseResponseDto {
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
