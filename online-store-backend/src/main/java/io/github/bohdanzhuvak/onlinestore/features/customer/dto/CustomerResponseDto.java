package io.github.bohdanzhuvak.onlinestore.features.customer.dto;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Base response DTO for customer functionality.
 * Contains only fields that can be returned to customer client.
 */
@Getter
@Setter
public abstract class CustomerResponseDto extends BaseResponseDto {

}
