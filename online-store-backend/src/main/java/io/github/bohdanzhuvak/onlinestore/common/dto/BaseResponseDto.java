package io.github.bohdanzhuvak.onlinestore.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Base DTO for response operations (read operations).
 * Contains all fields that can be returned to client.
 */
@Getter
@Setter
public abstract class BaseResponseDto implements BaseDto {
  private Long id;
}
