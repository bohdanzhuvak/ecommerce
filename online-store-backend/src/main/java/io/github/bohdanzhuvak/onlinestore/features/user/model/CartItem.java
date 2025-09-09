package io.github.bohdanzhuvak.onlinestore.features.user.model;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItem {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Cart cart;

  @ManyToOne
  private Product product;

  private int quantity;
}
