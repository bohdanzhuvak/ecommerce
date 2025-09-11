package io.github.bohdanzhuvak.onlinestore.features.admin.dto.category;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCategoryResponse extends AdminResponseDto {
  private Long id;
  private String name;
}
