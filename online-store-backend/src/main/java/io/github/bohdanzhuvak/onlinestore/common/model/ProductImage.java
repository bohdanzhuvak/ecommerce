package io.github.bohdanzhuvak.onlinestore.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductImage {
  @Id
  @GeneratedValue
  private Long id;

  private String url;

  @ManyToOne
  private Product product;
}
