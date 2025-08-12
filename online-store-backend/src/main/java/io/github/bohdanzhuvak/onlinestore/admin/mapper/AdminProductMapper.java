package io.github.bohdanzhuvak.onlinestore.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AdminProductMapper {
  @Mapping(target = "categoryName", source = "category.name")
  ProductResponse toResponse(Product product);

  @Mapping(target = "category.id", source = "categoryId")
  Product updateProduct(@MappingTarget Product existingProduct, UpdateProductRequest productRequest);

  Product createProduct(CreateProductRequest productResponse);

  default Page<ProductResponse> toResponsePage(Page<Product> products) {
    return products.map(this::toResponse);
  }
}
