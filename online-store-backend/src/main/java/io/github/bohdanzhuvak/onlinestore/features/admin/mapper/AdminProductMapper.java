package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.product.AdminProductRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.product.AdminProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminProductMapper extends AdminBaseMapper<Product, AdminProductResponse, AdminProductRequest> {
  /*@Mapping(target = "imageUrls", source = "images")
  @Mapping(target = "categoryName", source = "category.name")
  AdminProductResponse toResponse(Product product);

  @Mapping(target = "category.id", source = "categoryId")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", ignore = true)
  Product updateProduct(@MappingTarget Product existingProduct, UpdateProductRequest productRequest);

  @Mapping(target = "category.id", source = "categoryId")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", ignore = true)
    // Images are handled separately
  Product createProduct(AdminProductRequest productResponse);

  default Page<AdminProductResponse> toResponsePage(Page<Product> products) {
    return products.map(this::toResponse);
  }

  default List<String> map(List<ProductImage> images) {
    if (images == null) return null;
    return images.stream()
        .map(ProductImage::getUrl)
        .toList();
  }*/
}
