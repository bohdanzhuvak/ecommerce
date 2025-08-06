package io.github.bohdanzhuvak.onlinestore.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponse {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private String categoryName;
  private List<String> imageUrls;
}
