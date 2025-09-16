package io.github.bohdanzhuvak.onlinestore.features.customer.service.impl;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.repository.ProductRepository;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.mapper.ProductMapper;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  public Page<ProductResponse> getProducts(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    return productMapper.toResponsePage(products);
  }

  @Override
  public ProductResponse getProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    return productMapper.toResponse(product);
  }
}
