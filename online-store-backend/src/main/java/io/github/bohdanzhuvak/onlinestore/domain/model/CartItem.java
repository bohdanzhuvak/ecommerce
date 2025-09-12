package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

  @NotNull(message = "Cart is required")
  @ManyToOne
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  @NotNull(message = "Product is required")
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @NotNull(message = "Quantity is required")
  @Min(value = 1, message = "Quantity must be at least 1")
  @Column(nullable = false)
  private Integer quantity;
}
