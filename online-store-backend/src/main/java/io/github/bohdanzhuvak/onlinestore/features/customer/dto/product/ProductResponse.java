package io.github.bohdanzhuvak.onlinestore.features.customer.dto.product;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.CustomerResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductResponse extends CustomerResponseDto {
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private String categoryName;
  private List<String> imageUrls;
}
