package io.github.bohdanzhuvak.onlinestore.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AdminProductMapper {
  ProductResponse toResponse(Product product);

  Product updateProduct(@MappingTarget Product existingProduct, UpdateProductRequest productRequest);

  Product toProduct(ProductResponse productResponse);

  default Page<ProductResponse> toResponsePage(Page<Product> products) {
    return products.map(this::toResponse);
  }
}
