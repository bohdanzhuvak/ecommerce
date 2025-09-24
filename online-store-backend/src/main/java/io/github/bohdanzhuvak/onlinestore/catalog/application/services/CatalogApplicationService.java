package io.github.bohdanzhuvak.onlinestore.catalog.application.services;

import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.DeleteProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetAllProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.SearchProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CatalogApplicationService {
  private final GetProductsUseCase getProductsUseCase;
  private final GetProductUseCase getProductUseCase;
  private final SearchProductsUseCase searchProductsUseCase;
  private final GetAllProductsUseCase getAllProductsUseCase;
  private final CreateProductUseCase createProductUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;

  public CatalogApplicationService(GetProductsUseCase getProductsUseCase,
                                   GetProductUseCase getProductUseCase,
                                   SearchProductsUseCase searchProductsUseCase,
                                   GetAllProductsUseCase getAllProductsUseCase,
                                   CreateProductUseCase createProductUseCase,
                                   UpdateProductUseCase updateProductUseCase,
                                   DeleteProductUseCase deleteProductUseCase) {
    this.getProductsUseCase = getProductsUseCase;
    this.getProductUseCase = getProductUseCase;
    this.searchProductsUseCase = searchProductsUseCase;
    this.getAllProductsUseCase = getAllProductsUseCase;
    this.createProductUseCase = createProductUseCase;
    this.updateProductUseCase = updateProductUseCase;
    this.deleteProductUseCase = deleteProductUseCase;
  }

  // Customer methods
  public List<Product> getActiveProducts() {
    return getProductsUseCase.execute();
  }

  public List<Product> getActiveProducts(int offset, int limit) {
    return getProductsUseCase.execute(offset, limit);
  }

  public Product getActiveProduct(ProductId productId) {
    return getProductUseCase.execute(productId);
  }

  public List<Product> searchProductsByName(String name) {
    return searchProductsUseCase.searchByName(name);
  }

  public List<Product> searchProductsByCategory(CategoryId categoryId) {
    return searchProductsUseCase.searchByCategory(categoryId);
  }

  public List<Product> searchProductsByPriceRange(Money minPrice, Money maxPrice) {
    return searchProductsUseCase.searchByPriceRange(minPrice, maxPrice);
  }

  public List<Product> searchAvailableProducts() {
    return searchProductsUseCase.searchAvailable();
  }

  // Admin methods
  public List<Product> getAllProducts() {
    return getAllProductsUseCase.execute();
  }

  public List<Product> getAllProducts(int offset, int limit) {
    return getAllProductsUseCase.execute(offset, limit);
  }

  public Optional<Product> getProduct(ProductId productId) {
    return getAllProductsUseCase.execute(productId);
  }

  public Product createProduct(CreateProductUseCase.CreateProductCommand command) {
    return createProductUseCase.execute(command);
  }

  public Product updateProduct(UpdateProductUseCase.UpdateProductCommand command) {
    return updateProductUseCase.execute(command);
  }

  public void deleteProduct(ProductId productId) {
    deleteProductUseCase.execute(productId);
  }
}
