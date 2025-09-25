package io.github.bohdanzhuvak.onlinestore.cart.domain;

public class ProductSnapshot {
  private final ProductId id;
  private final String name;
  private final Money price;

  private ProductSnapshot(ProductId productId, String name, Money price) {
    this.id = productId;
    this.name = name;
    this.price = price;
  }

  public static ProductSnapshot of(ProductId productId, String name, Money price) {
    return new ProductSnapshot(productId, name, price);
  }

  public ProductId getProductId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }
}
