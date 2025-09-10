package io.github.bohdanzhuvak.onlinestore.features.customer.model;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
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
