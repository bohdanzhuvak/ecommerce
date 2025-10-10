package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product;

import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AdminProductResponse extends AdminResponseDto {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private Long categoryId;
  private String categoryName;
  private List<String> imageUrls;
}
