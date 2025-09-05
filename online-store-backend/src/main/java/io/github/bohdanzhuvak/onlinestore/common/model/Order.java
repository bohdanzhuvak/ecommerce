package io.github.bohdanzhuvak.onlinestore.common.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items;

  private BigDecimal totalPrice;

  @CreatedDate
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
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
