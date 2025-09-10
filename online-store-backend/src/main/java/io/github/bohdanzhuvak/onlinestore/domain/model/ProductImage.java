package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_images")
public class ProductImage extends BaseEntity {

  @NotBlank(message = "Image URL is required")
  @Size(max = 500, message = "Image URL must not exceed 500 characters")
  @Column(nullable = false, length = 500)
  private String url;

  @NotNull(message = "Product is required")
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  public ProductImage(String url, Product product) {
    this.url = url;
    this.product = product;
  }
}
