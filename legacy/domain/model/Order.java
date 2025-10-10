package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name = "orders")
public class Order extends AuditableEntity {

  @NotNull(message = "User is required")
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();

  @NotNull(message = "Total price is required")
  @DecimalMin(value = "0.0", message = "Total price must be non-negative")
  @Digits(integer = 8, fraction = 2, message = "Total price must have at most 8 integer digits and 2 decimal places")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal totalPrice;

  @NotNull(message = "Order status is required")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private OrderStatus status;

  @ManyToOne
  @JoinColumn(name = "delivery_address_id")
  private DeliveryAddress deliveryAddress;

  public void addItem(OrderItem item) {
    if (items == null) {
      items = new ArrayList<>();
    }
    items.add(item);
    item.setOrder(this);
  }
}
