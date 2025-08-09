package io.github.bohdanzhuvak.onlinestore.admin.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private Long categoryId;
}
