package io.github.bohdanzhuvak.onlinestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
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
