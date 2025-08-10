package io.github.bohdanzhuvak.onlinestore.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItem {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Order order;

  @ManyToOne
  private Product product;

  private int quantity;

  private BigDecimal pricePerUnit;
}
