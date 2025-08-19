package io.github.bohdanzhuvak.onlinestore.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.common.model.ProductImage;
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
  Product updateProduct(@MappingTarget Product existingProduct, UpdateProductRequest productRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category.id", source = "categoryId")
  @Mapping(target = "images", ignore = true) // Images are handled separately
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
