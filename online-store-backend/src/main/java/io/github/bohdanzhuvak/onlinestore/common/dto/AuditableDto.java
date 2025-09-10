package io.github.bohdanzhuvak.onlinestore.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AuditableDto extends BaseDto {
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
