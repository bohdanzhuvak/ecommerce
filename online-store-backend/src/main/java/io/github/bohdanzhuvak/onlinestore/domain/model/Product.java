package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {

  @NotBlank(message = "Product name is required")
  @Size(max = 100, message = "Product name must not exceed 100 characters")
  @Column(nullable = false, length = 100)
  private String name;

  @Size(max = 1000, message = "Description must not exceed 1000 characters")
  @Column(length = 1000)
  private String description;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
  @Digits(integer = 8, fraction = 2, message = "Price must have at most 8 integer digits and 2 decimal places")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @NotNull(message = "Stock is required")
  @Min(value = 0, message = "Stock must be non-negative")
  @Column(nullable = false)
  private Integer stock = 0;

  @ManyToOne
  private Category category;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductImage> images = new ArrayList<>();
}
