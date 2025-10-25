package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.catalog.application.ports.ExternalCatalogOrchestrator;
import io.github.bohdanzhuvak.onlinestore.catalog.application.services.ExternalCatalogOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.catalog.application.services.WebCatalogOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.CreateProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.DeleteCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.DeleteProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetActiveProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetAllCategoriesUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetAllProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.SearchProductsUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateCategoryUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.UpdateProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfig {
  @Bean
  public ExternalCatalogOrchestrator externalCatalogOrchestrator(GetActiveProductUseCase getActiveProductUseCase) {
    return new ExternalCatalogOrchestratorService(getActiveProductUseCase);
  }

  @Bean
  public WebCatalogOrchestratorService webCatalogOrchestratorService(
      GetProductsUseCase getProductsUseCase,
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
      DeleteCategoryUseCase deleteCategoryUseCase
  ) {
    return new WebCatalogOrchestratorService(
        getProductsUseCase,
        getProductUseCase,
        searchProductsUseCase,
        getAllProductsUseCase,
        createProductUseCase,
        updateProductUseCase,
        deleteProductUseCase,
        getAllCategoriesUseCase,
        getCategoryUseCase,
        createCategoryUseCase,
        updateCategoryUseCase,
        deleteCategoryUseCase
    );
  }
}
