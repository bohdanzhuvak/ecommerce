package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

  @NotNull(message = "Order is required")
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @NotNull(message = "Product is required")
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @NotNull(message = "Quantity is required")
  @Min(value = 1, message = "Quantity must be at least 1")
  @Column(nullable = false)
  private Integer quantity;

  @NotNull(message = "Price per unit is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price per unit must be positive")
  @Digits(integer = 8, fraction = 2, message = "Price per unit must have at most 8 integer digits and 2 decimal places")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal pricePerUnit;
}
