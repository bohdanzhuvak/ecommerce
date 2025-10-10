package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "carts")
public class Cart extends AuditableEntity {

  @NotNull(message = "User is required")
  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
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

  public void clear() {
    items.clear();
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
