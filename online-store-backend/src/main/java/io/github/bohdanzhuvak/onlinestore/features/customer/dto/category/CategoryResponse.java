package io.github.bohdanzhuvak.onlinestore.features.customer.dto.category;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.CustomerResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends CustomerResponseDto {
  private String name;
}
