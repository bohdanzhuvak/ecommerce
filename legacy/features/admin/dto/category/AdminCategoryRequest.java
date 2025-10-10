package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.category;

import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCategoryRequest extends AdminRequestDto {
  private String name;
}
