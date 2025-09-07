package io.github.bohdanzhuvak.onlinestore.admin.service;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.mapper.AdminProductMapper;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.common.model.ProductImage;
import io.github.bohdanzhuvak.onlinestore.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductService {
  private final ProductRepository productRepository;
  private final AdminProductMapper adminProductMapper;

  public Page<ProductResponse> getProducts(Pageable pageable) {
    Page<Product> productPage = productRepository.findAll(pageable);
    return adminProductMapper.toResponsePage(productPage);
  }

  public ProductResponse getProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    return adminProductMapper.toResponse(product);
  }

  public ProductResponse addProduct(CreateProductRequest productRequest) {
    Product product = adminProductMapper.createProduct(productRequest);
    Product finalProduct = product;
    product.setImages(productRequest.getImageUrls().stream()
        .map(url -> new ProductImage(null, url, finalProduct))
        .toList());
    product = productRepository.save(product);
    return adminProductMapper.toResponse(product);
  }

  public ProductResponse updateProduct(Long id, UpdateProductRequest product) {
    Product existingProduct = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found with id: " + id));
    Product updatedProduct = adminProductMapper.updateProduct(existingProduct, product);
    Product savedProduct = productRepository.save(updatedProduct);
    return adminProductMapper.toResponse(savedProduct);
  }

  public void deleteProduct(Long id) {
    if (!productRepository.existsById(id)) {
      throw new NotFoundException("Product not found with id: " + id);
    }
    productRepository.deleteById(id);
  }
}
