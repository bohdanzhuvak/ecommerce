package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", nullable = false)
  private CartEntity cart;

  @Column(name = "product_id", nullable = false)
  private String productId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "unit_price_amount", nullable = false, precision = 19, scale = 2)
  private BigDecimal unitPriceAmount;

  @Column(name = "unit_price_currency", nullable = false, length = 3)
  private String unitPriceCurrency;

  @Column(nullable = false)
  private Integer quantity;

  // Constructors
  public CartItemEntity() {
  }

  public CartItemEntity(Long id, CartEntity cart, String productId, String productName,
                        BigDecimal unitPriceAmount, String unitPriceCurrency, Integer quantity) {
    this.id = id;
    this.cart = cart;
    this.productId = productId;
    this.productName = productName;
    this.unitPriceAmount = unitPriceAmount;
    this.unitPriceCurrency = unitPriceCurrency;
    this.quantity = quantity;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CartEntity getCart() {
    return cart;
  }

  public void setCart(CartEntity cart) {
    this.cart = cart;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getUnitPriceAmount() {
    return unitPriceAmount;
  }

  public void setUnitPriceAmount(BigDecimal unitPriceAmount) {
    this.unitPriceAmount = unitPriceAmount;
  }

  public String getUnitPriceCurrency() {
    return unitPriceCurrency;
  }

  public void setUnitPriceCurrency(String unitPriceCurrency) {
    this.unitPriceCurrency = unitPriceCurrency;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
