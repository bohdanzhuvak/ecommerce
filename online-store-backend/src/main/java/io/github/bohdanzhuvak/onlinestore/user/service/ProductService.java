package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.common.repository.ProductRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.user.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public Page<ProductResponse> getProducts(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    return productMapper.toResponsePage(products);
  }

  public ProductResponse getProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    return productMapper.toResponse(product);
  }
}
