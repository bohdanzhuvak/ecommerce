package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto;

import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Base request DTO for admin functionality.
 * Contains only fields that can be provided by admin client.
 */
@Getter
@Setter
public abstract class AdminRequestDto extends BaseRequestDto {


}
