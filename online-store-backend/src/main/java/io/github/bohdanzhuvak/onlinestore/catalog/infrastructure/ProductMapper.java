package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

  public Product toDomain(ProductEntity entity) {
    return Product.restore(
        ProductId.of(entity.getId()),
        entity.getName(),
        entity.getDescription(),
        Money.of(entity.getPriceAmount(), entity.getPriceCurrency()),
        entity.getStock(),
        CategoryId.of(entity.getCategoryId()),
        entity.getActive(),
        entity.getImages(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }

  public ProductEntity toEntity(Product product) {
    return new ProductEntity(
        product.getId().getValue(),
        product.getName(),
        product.getDescription(),
        product.getPrice().getAmount(),
        product.getPrice().getCurrency(),
        product.getStock(),
        product.getCategoryId().getValue(),
        product.isActive(),
        product.getImages(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }

  public List<Product> toDomainList(List<ProductEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  public List<ProductEntity> toEntityList(List<Product> products) {
    return products.stream()
        .map(this::toEntity)
        .collect(Collectors.toList());
  }
}
