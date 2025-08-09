package io.github.bohdanzhuvak.onlinestore.user.model;

import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cart {
  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> items = new ArrayList<>();

  public void addItem(Product product, int quantity) {
    findItemByProduct(product).ifPresentOrElse(
        item -> item.setQuantity(item.getQuantity() + quantity),
        () -> items.add(createCartItem(product, quantity))
    );
  }

  public void updateItem(Product product, int quantity) {
    findItemByProduct(product).ifPresentOrElse(
        item -> {
          if (quantity <= 0) {
            items.remove(item);
          } else {
            item.setQuantity(quantity);
          }
        },
        () -> {
          if (quantity > 0) {
            items.add(createCartItem(product, quantity));
          }
        }
    );
  }

  private Optional<CartItem> findItemByProduct(Product product) {
    return items.stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst();
  }

  private CartItem createCartItem(Product product, int quantity) {
    return CartItem.builder()
        .product(product)
        .quantity(quantity)
        .cart(this)
        .build();
  }

  public BigDecimal getTotalPrice() {
    return items.stream()
        .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
