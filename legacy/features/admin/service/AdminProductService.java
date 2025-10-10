package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CategoryRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.ProductRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product.AdminProductRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product.AdminProductResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper.AdminProductMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminProductService extends AbstractAdminFullAccessService<Product, AdminProductResponse, AdminProductRequest, Long> {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final AdminProductMapper adminProductMapper;

  @Override
  protected ProductRepository getRepository() {
    return productRepository;
  }

  @Override
  protected AdminProductMapper getMapper() {
    return adminProductMapper;
  }

  @Override
  @Transactional
  public AdminProductResponse save(AdminProductRequest dto) {
    Product product = getMapper().toEntity(dto);

    // Load category if categoryId is provided
    if (dto.getCategoryId() != null) {
      Category category = categoryRepository.findById(dto.getCategoryId())
          .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
      product.setCategory(category);
    }

    Product saved = productRepository.save(product);
    return getMapper().toResponseDto(saved);
  }

  @Override
  @Transactional
  public AdminProductResponse update(Long id, AdminProductRequest dto) {
    Product existing = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    getMapper().updateEntity(existing, dto);

    // Load category if categoryId is provided
    if (dto.getCategoryId() != null) {
      Category category = categoryRepository.findById(dto.getCategoryId())
          .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
      existing.setCategory(category);
    }

    Product updated = productRepository.save(existing);
    return getMapper().toResponseDto(updated);
  }

  /**
   * Override field specification to handle Product-specific filtering
   */
  @Override
  protected Specification<Product> createFieldSpecification(String field, Object value) {
    return (root, query, cb) -> {
      try {
        switch (field) {
          case "name":
            return handleStringField(root, cb, "name", value);
          case "description":
            return handleStringField(root, cb, "description", value);
          case "price":
            return handlePriceField(root, cb, value);
          case "stock":
            return handleNumericField(root, cb, "stock", value);
          case "category":
            return handleCategoryField(root, cb, value);
          case "category.name":
            return handleCategoryNameField(root, cb, value);
          case "category.id":
            return handleCategoryIdField(root, cb, value);
          default:
            // Fallback to parent implementation
            return super.createFieldSpecification(field, value).toPredicate(root, query, cb);
        }
      } catch (Exception e) {
        return null;
      }
    };
  }

  private Predicate handleStringField(Root<Product> root, CriteriaBuilder cb, String fieldName, Object value) {
    if (value instanceof String stringValue) {
      if (stringValue.contains("%") || stringValue.contains("_")) {
        return cb.like(root.get(fieldName), stringValue);
      } else {
        return cb.like(cb.lower(root.get(fieldName)), "%" + stringValue.toLowerCase() + "%");
      }
    }
    return cb.equal(root.get(fieldName), value);
  }

  private Predicate handlePriceField(Root<Product> root, CriteriaBuilder cb, Object value) {
    if (value instanceof String) {
      try {
        BigDecimal price = new BigDecimal((String) value);
        return cb.equal(root.get("price"), price);
      } catch (NumberFormatException e) {
        return null;
      }
    } else if (value instanceof Number) {
      return cb.equal(root.get("price"), value);
    }
    return cb.equal(root.get("price"), value);
  }

  private Predicate handleNumericField(Root<Product> root, CriteriaBuilder cb, String fieldName, Object value) {
    if (value instanceof String) {
      try {
        Integer intValue = Integer.parseInt((String) value);
        return cb.equal(root.get(fieldName), intValue);
      } catch (NumberFormatException e) {
        return null;
      }
    } else if (value instanceof Number) {
      return cb.equal(root.get(fieldName), value);
    }
    return cb.equal(root.get(fieldName), value);
  }

  private Predicate handleCategoryField(Root<Product> root, CriteriaBuilder cb, Object value) {
    if (value instanceof String) {
      try {
        Long categoryId = Long.parseLong((String) value);
        return cb.equal(root.get("category").get("id"), categoryId);
      } catch (NumberFormatException e) {
        return null;
      }
    } else if (value instanceof Number) {
      return cb.equal(root.get("category").get("id"), value);
    }
    return cb.equal(root.get("category"), value);
  }

  private Predicate handleCategoryNameField(Root<Product> root, CriteriaBuilder cb, Object value) {
    if (value instanceof String stringValue) {
      if (stringValue.contains("%") || stringValue.contains("_")) {
        return cb.like(root.get("category").get("name"), stringValue);
      } else {
        return cb.like(cb.lower(root.get("category").get("name")), "%" + stringValue.toLowerCase() + "%");
      }
    }
    return cb.equal(root.get("category").get("name"), value);
  }

  private Predicate handleCategoryIdField(Root<Product> root, CriteriaBuilder cb, Object value) {
    if (value instanceof String) {
      try {
        Long categoryId = Long.parseLong((String) value);
        return cb.equal(root.get("category").get("id"), categoryId);
      } catch (NumberFormatException e) {
        return null;
      }
    } else if (value instanceof Number) {
      return cb.equal(root.get("category").get("id"), value);
    }
    return cb.equal(root.get("category").get("id"), value);
  }
}
