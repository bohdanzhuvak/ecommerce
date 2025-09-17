package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Product {
  private final ProductId id;
  private String name;
  private String description;
  private Money price;
  private int stock;
  private CategoryId categoryId;
  private boolean active;
  private final List<String> images;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Constructor for creating a new product
  public Product(ProductId id, String name, String description, Money price,
                 int stock, CategoryId categoryId) {
    if (id == null) {
      throw new IllegalArgumentException("Product ID cannot be null");
    }
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    if (price == null) {
      throw new IllegalArgumentException("Product price cannot be null");
    }
    if (stock < 0) {
      throw new IllegalArgumentException("Stock cannot be negative");
    }
    if (categoryId == null) {
      throw new IllegalArgumentException("Category ID cannot be null");
    }

    this.id = id;
    this.name = name.trim();
    this.description = description != null ? description.trim() : "";
    this.price = price;
    this.stock = stock;
    this.categoryId = categoryId;
    this.active = true;
    this.images = new ArrayList<>();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Constructor for restoring from database
  private Product(ProductId id, String name, String description, Money price,
                  int stock, CategoryId categoryId, boolean active,
                  List<String> images, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.categoryId = categoryId;
    this.active = active;
    this.images = new ArrayList<>(images);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for restoring from database
  public static Product restore(ProductId id, String name, String description, Money price,
                                int stock, CategoryId categoryId, boolean active,
                                List<String> images, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new Product(id, name, description, price, stock, categoryId, active,
        images != null ? images : new ArrayList<>(), createdAt, updatedAt);
  }

  // Business methods
  public void updateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    this.name = name.trim();
    this.updatedAt = LocalDateTime.now();
  }

  public void updateDescription(String description) {
    this.description = description != null ? description.trim() : "";
    this.updatedAt = LocalDateTime.now();
  }

  public void updatePrice(Money price) {
    if (price == null) {
      throw new IllegalArgumentException("Product price cannot be null");
    }
    this.price = price;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateStock(int stock) {
    if (stock < 0) {
      throw new IllegalArgumentException("Stock cannot be negative");
    }
    this.stock = stock;
    this.updatedAt = LocalDateTime.now();
  }

  public void changeCategory(CategoryId categoryId) {
    if (categoryId == null) {
      throw new IllegalArgumentException("Category ID cannot be null");
    }
    this.categoryId = categoryId;
    this.updatedAt = LocalDateTime.now();
  }

  public void activate() {
    this.active = true;
    this.updatedAt = LocalDateTime.now();
  }

  public void deactivate() {
    this.active = false;
    this.updatedAt = LocalDateTime.now();
  }

  public void addImage(String imageUrl) {
    if (imageUrl == null || imageUrl.trim().isEmpty()) {
      throw new IllegalArgumentException("Image URL cannot be null or empty");
    }
    this.images.add(imageUrl.trim());
    this.updatedAt = LocalDateTime.now();
  }

  public void removeImage(String imageUrl) {
    this.images.remove(imageUrl);
    this.updatedAt = LocalDateTime.now();
  }

  public void reserveStock(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Reservation quantity must be positive");
    }
    if (quantity > this.stock) {
      throw new IllegalArgumentException("Insufficient stock. Available: " + this.stock + ", requested: " + quantity);
    }
    this.stock -= quantity;
    this.updatedAt = LocalDateTime.now();
  }

  public void releaseStock(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Release quantity must be positive");
    }
    this.stock += quantity;
    this.updatedAt = LocalDateTime.now();
  }

  public boolean isAvailable() {
    return active && stock > 0;
  }

  public boolean hasStock(int quantity) {
    return stock >= quantity;
  }

  // Getters
  public ProductId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Money getPrice() {
    return price;
  }

  public int getStock() {
    return stock;
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public boolean isActive() {
    return active;
  }

  public List<String> getImages() {
    return Collections.unmodifiableList(images);
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", stock=" + stock +
        ", active=" + active +
        '}';
  }
}
