package io.github.bohdanzhuvak.onlinestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product {
  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String description;
  private BigDecimal price;
  private int stock;

  @ManyToOne
  private Category category;

  @OneToMany(mappedBy = "product")
  private List<ProductImage> images;
}
