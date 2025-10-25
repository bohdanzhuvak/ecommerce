package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Category {
  private final CategoryId id;
  private final LocalDateTime createdAt;
  private String name;
  private String description;
  private LocalDateTime updatedAt;

  // Constructor for creating a new category
  public Category(CategoryId id, String name, String description) {
    if (id == null) {
      throw new IllegalArgumentException("Category ID cannot be null");
    }
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Category name cannot be null or empty");
    }

    this.id = id;
    this.name = name.trim();
    this.description = description != null ? description.trim() : "";
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Constructor for restoring from database
  private Category(CategoryId id, String name, String description,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for restoring from database
  public static Category restore(CategoryId id, String name, String description,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new Category(id, name, description, createdAt, updatedAt);
  }

  // Business methods
  public void updateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Category name cannot be null or empty");
    }
    this.name = name.trim();
    this.updatedAt = LocalDateTime.now();
  }

  public void updateDescription(String description) {
    this.description = description != null ? description.trim() : "";
    this.updatedAt = LocalDateTime.now();
  }

  // Getters
  public CategoryId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
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
    Category category = (Category) o;
    return Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
