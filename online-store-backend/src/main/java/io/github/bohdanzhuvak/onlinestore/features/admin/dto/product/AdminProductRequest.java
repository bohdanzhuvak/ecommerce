package io.github.bohdanzhuvak.onlinestore.features.admin.dto.product;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AdminProductRequest extends AdminRequestDto {
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private Long categoryId;
  private List<String> imageUrls;
}
