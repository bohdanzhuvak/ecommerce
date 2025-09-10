package io.github.bohdanzhuvak.onlinestore.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.model.ProductImage;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(target = "imageUrls", source = "images")
  @Mapping(target = "categoryName", source = "category.name")
  ProductResponse toResponse(Product product);

  default Page<ProductResponse> toResponsePage(Page<Product> products) {
    return products.map(this::toResponse);
  }

  default List<String> map(List<ProductImage> images) {
    if (images == null) return null;
    return images.stream()
        .map(ProductImage::getUrl)
        .toList();
  }
}
