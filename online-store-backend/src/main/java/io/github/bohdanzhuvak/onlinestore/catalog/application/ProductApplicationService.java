package io.github.bohdanzhuvak.onlinestore.catalog.application;

import io.github.bohdanzhuvak.onlinestore.catalog.application.admin.CreateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.admin.DeleteProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.admin.GetAllProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.admin.UpdateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.customer.GetProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.customer.GetProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.customer.SearchProductsUseCase;
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
public class ProductApplicationService {
  private final GetProductsUseCase getProductsUseCase;
  private final GetProductUseCase getProductUseCase;
  private final SearchProductsUseCase searchProductsUseCase;
  private final GetAllProductsUseCase getAllProductsUseCase;
  private final CreateProductUseCase createProductUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;

  public ProductApplicationService(GetProductsUseCase getProductsUseCase,
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

  // Клиентские операции
  public List<Product> getActiveProducts() {
    return getProductsUseCase.execute();
  }

  public List<Product> getActiveProducts(int offset, int limit) {
    return getProductsUseCase.execute(offset, limit);
  }

  public Optional<Product> getActiveProduct(ProductId productId) {
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

  // Админские операции
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
