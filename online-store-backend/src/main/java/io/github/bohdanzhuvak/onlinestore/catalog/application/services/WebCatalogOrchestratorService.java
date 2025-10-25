package io.github.bohdanzhuvak.onlinestore.catalog.application.services;

import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.DeleteCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.DeleteProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetAllCategoriesUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetAllProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.SearchProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

import java.util.List;
import java.util.Optional;

public class WebCatalogOrchestratorService {
  private final GetProductsUseCase getProductsUseCase;
  private final GetProductUseCase getProductUseCase;
  private final SearchProductsUseCase searchProductsUseCase;
  private final GetAllProductsUseCase getAllProductsUseCase;
  private final CreateProductUseCase createProductUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;
  private final GetAllCategoriesUseCase getAllCategoriesUseCase;
  private final GetCategoryUseCase getCategoryUseCase;
  private final CreateCategoryUseCase createCategoryUseCase;
  private final UpdateCategoryUseCase updateCategoryUseCase;
  private final DeleteCategoryUseCase deleteCategoryUseCase;

  public WebCatalogOrchestratorService(GetProductsUseCase getProductsUseCase,
                                       GetProductUseCase getProductUseCase,
                                       SearchProductsUseCase searchProductsUseCase,
                                       GetAllProductsUseCase getAllProductsUseCase,
                                       CreateProductUseCase createProductUseCase,
                                       UpdateProductUseCase updateProductUseCase,
                                       DeleteProductUseCase deleteProductUseCase,
                                       GetAllCategoriesUseCase getAllCategoriesUseCase,
                                       GetCategoryUseCase getCategoryUseCase,
                                       CreateCategoryUseCase createCategoryUseCase,
                                       UpdateCategoryUseCase updateCategoryUseCase,
                                       DeleteCategoryUseCase deleteCategoryUseCase) {
    this.getProductsUseCase = getProductsUseCase;
    this.getProductUseCase = getProductUseCase;
    this.searchProductsUseCase = searchProductsUseCase;
    this.getAllProductsUseCase = getAllProductsUseCase;
    this.createProductUseCase = createProductUseCase;
    this.updateProductUseCase = updateProductUseCase;
    this.deleteProductUseCase = deleteProductUseCase;
    this.getAllCategoriesUseCase = getAllCategoriesUseCase;
    this.getCategoryUseCase = getCategoryUseCase;
    this.createCategoryUseCase = createCategoryUseCase;
    this.updateCategoryUseCase = updateCategoryUseCase;
    this.deleteCategoryUseCase = deleteCategoryUseCase;
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

  // Category methods
  public List<Category> getAllCategories() {
    return getAllCategoriesUseCase.execute();
  }

  public List<Category> getAllCategories(int offset, int limit) {
    return getAllCategoriesUseCase.execute(offset, limit);
  }

  public Optional<Category> getCategory(CategoryId categoryId) {
    return getCategoryUseCase.execute(categoryId);
  }

  public Category createCategory(CreateCategoryUseCase.CreateCategoryCommand command) {
    return createCategoryUseCase.execute(command);
  }

  public Category updateCategory(UpdateCategoryUseCase.UpdateCategoryCommand command) {
    return updateCategoryUseCase.execute(command);
  }

  public void deleteCategory(CategoryId categoryId) {
    deleteCategoryUseCase.execute(categoryId);
  }
}
