package io.github.bohdanzhuvak.onlinestore.order.domain;

public class CartItemSnapshot {
  private final String productId;
  private final String productName;
  private final int quantity;
  private final Money pricePerUnit;

  private CartItemSnapshot(String productId, String productName, int quantity, Money pricePerUnit) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.pricePerUnit = pricePerUnit;
  }

  public static CartItemSnapshot of(String productId, String productName, int quantity, Money pricePerUnit) {
    return new CartItemSnapshot(productId, productName, quantity, pricePerUnit);
  }

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public Money getPricePerUnit() {
    return pricePerUnit;
  }
}
