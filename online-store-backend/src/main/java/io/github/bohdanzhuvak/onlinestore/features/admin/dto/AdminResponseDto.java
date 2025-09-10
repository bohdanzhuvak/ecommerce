package io.github.bohdanzhuvak.onlinestore.features.admin.dto;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Base response DTO for admin functionality with react-admin support.
 * Extends BaseResponseDto with additional admin-specific metadata.
 */
@Getter
@Setter
public abstract class AdminResponseDto extends BaseResponseDto {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  /**
   * Additional metadata for react-admin
   * Can contain custom fields, permissions, etc.
   */
  private Map<String, Object> metadata;

  /**
   * Whether this record can be edited by admin
   */
  private Boolean editable = true;

  /**
   * Whether this record can be deleted by admin
   */
  private Boolean deletable = true;

  /**
   * Display name for react-admin lists
   */
  private String displayName;
}
