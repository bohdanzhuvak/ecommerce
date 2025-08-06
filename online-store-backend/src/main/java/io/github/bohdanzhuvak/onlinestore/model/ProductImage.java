package io.github.bohdanzhuvak.onlinestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductImage {
  @Id
  @GeneratedValue
  private Long id;

  private String url;

  @ManyToOne
  private Product product;
}
