package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.model.ProductImage;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product.AdminProductRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product.AdminProductResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminProductMapper extends AdminBaseMapper<Product, AdminProductResponse, AdminProductRequest> {

  @Override
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "stock", source = "stock")
  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "imageUrls", source = "images", qualifiedByName = "mapImages")
  @Mapping(target = "categoryName", source = "category.name")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "updatedAt", source = "updatedAt")
  @Mapping(target = "metadata", ignore = true)
  @Mapping(target = "editable", ignore = true)
  @Mapping(target = "deletable", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  AdminProductResponse toResponseDto(Product product);

  @Override
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "stock", source = "stock")
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapImageUrlsToEntities")
  Product toEntity(AdminProductRequest dto);

  @Override
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "stock", source = "stock")
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", source = "imageUrls", qualifiedByName = "mapImageUrlsToEntities")
  void updateEntity(@MappingTarget Product entity, AdminProductRequest dto);

  @Named("mapImages")
  default List<String> mapImages(List<ProductImage> images) {
    if (images == null) return null;
    return images.stream()
        .map(ProductImage::getUrl)
        .toList();
  }

  @Named("mapImageUrlsToEntities")
  default List<ProductImage> mapImageUrlsToEntities(List<String> urls) {
    if (urls == null) return new ArrayList<>();
    List<ProductImage> images = new ArrayList<>();
    for (String url : urls) {
      ProductImage image = new ProductImage();
      image.setUrl(url);
      images.add(image);
    }
    return images;
  }

  @AfterMapping
  default void linkImagesToProduct(@MappingTarget Product product) {
    if (product.getImages() != null) {
      product.getImages().forEach(img -> img.setProduct(product));
    }
  }
}
