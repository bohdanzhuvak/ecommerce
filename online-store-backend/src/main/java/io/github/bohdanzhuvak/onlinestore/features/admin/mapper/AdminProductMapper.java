package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.model.ProductImage;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.product.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.product.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdminProductMapper {
  @Mapping(target = "imageUrls", source = "images")
  @Mapping(target = "categoryName", source = "category.name")
  ProductResponse toResponse(Product product);

  @Mapping(target = "category.id", source = "categoryId")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", ignore = true)
  Product updateProduct(@MappingTarget Product existingProduct, UpdateProductRequest productRequest);

  @Mapping(target = "category.id", source = "categoryId")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", ignore = true)
    // Images are handled separately
  Product createProduct(CreateProductRequest productResponse);

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
