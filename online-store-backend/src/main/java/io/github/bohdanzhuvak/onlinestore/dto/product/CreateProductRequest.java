package io.github.bohdanzhuvak.onlinestore.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductRequest {
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private Long categoryId;
  private List<String> imageUrls;
}
