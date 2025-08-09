package io.github.bohdanzhuvak.onlinestore.admin.service;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.mapper.AdminProductMapper;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductService {
  private final ProductRepository productRepository;
  private final AdminProductMapper adminProductMapper;

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
