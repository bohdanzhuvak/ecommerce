package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.legacy.common.service.AbstractFullAccessService;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract implementation of AdminFullAccessService.
 * Provides admin-specific functionality with react-admin support.
 */
public abstract class AbstractAdminFullAccessService<E extends BaseEntity, R extends AdminResponseDto, S extends AdminRequestDto, ID>
    extends AbstractFullAccessService<E, R, S, ID> implements AdminFullAccessService<E, R, S, ID> {

  @Override
  public Page<R> findAllWithFilters(
      String sort,
      String order,
      int page,
      int perPage,
      Map<String, Object> filters) {

    Sort.Direction direction = (order != null && order.equalsIgnoreCase("DESC"))
        ? Sort.Direction.DESC
        : Sort.Direction.ASC;

    String sortField = (sort != null) ? sort : "id";
    Sort sortObj = Sort.by(direction, sortField);

    Pageable pageable = PageRequest.of(page - 1, perPage, sortObj);

    if (filters != null && !filters.isEmpty()) {
      return findByFiltersWithPagination(filters, pageable);
    }

    Page<E> entities = getRepository().findAll(pageable);
    return getMapper().toResponseDtoPage(entities);
  }

  @Override
  public List<R> findByFilters(Map<String, String> filters) {
    // Basic implementation - can be overridden in concrete services
    List<E> entities = getRepository().findAll();
    return getMapper().toResponseDtoList(entities);
  }

  @Override
  public boolean hasPermission(ID entityId, String operation) {
    // Basic implementation - always return true
    // Should be overridden in concrete services with actual permission logic
    return true;
  }

  @Override
  public Map<String, Object> getStatistics() {
    Map<String, Object> stats = new HashMap<>();
    stats.put("total", getRepository().count());
    stats.put("timestamp", System.currentTimeMillis());
    return stats;
  }

  @Override
  public List<R> findAllForReference() {
    List<E> entities = getRepository().findAll();
    return getMapper().toResponseDtoList(entities);
  }

  /**
   * Find entities with filters and pagination
   * Can be overridden in concrete services for specific filtering logic
   */
  protected Page<R> findByFiltersWithPagination(Map<String, Object> filters, Pageable pageable) {
    Specification<E> spec = createSpecificationFromFilters(filters);

    if (spec == null) {
      Page<E> entities = getRepository().findAll(pageable);
      return getMapper().toResponseDtoPage(entities);
    }

    // Temporarily cast to JPA repository for specification support
    Page<E> entities = ((org.springframework.data.jpa.repository.JpaSpecificationExecutor<E>) getRepository()).findAll(spec, pageable);
    return getMapper().toResponseDtoPage(entities);
  }

  /**
   * Create JPA Specification from filter map
   * Handles different field types and operations
   */
  protected Specification<E> createSpecificationFromFilters(Map<String, Object> filters) {
    if (filters == null || filters.isEmpty()) {
      return null;
    }

    Specification<E> spec = null;

    for (Map.Entry<String, Object> entry : filters.entrySet()) {
      String field = entry.getKey();
      Object value = entry.getValue();

      if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
        continue; // Skip null or empty values
      }

      Specification<E> fieldSpec = createFieldSpecification(field, value);

      if (fieldSpec != null) {
        if (spec == null) {
          spec = fieldSpec;
        } else {
          spec = spec.and(fieldSpec);
        }
      }
    }

    return spec;
  }

  /**
   * Create specification for a single field
   * Can be overridden in concrete services for custom field handling
   */
  protected Specification<E> createFieldSpecification(String field, Object value) {
    return (root, query, cb) -> {
      try {
        String[] fieldParts = field.split("\\.");
        var path = root.get(fieldParts[0]);

        for (int i = 1; i < fieldParts.length; i++) {
          path = path.get(fieldParts[i]);
        }

        if (value instanceof String stringValue) {
          if (stringValue.contains("%") || stringValue.contains("_")) {
            return cb.like(path.as(String.class), stringValue);
          } else {
            return cb.like(cb.lower(path.as(String.class)), "%" + stringValue.toLowerCase() + "%");
          }
        } else if (value instanceof Number || value instanceof Boolean) {
          return cb.equal(path, value);
        } else if (value instanceof List<?> listValue) {
          // поддержка IN (...) для списков
          CriteriaBuilder.In<Object> inClause = cb.in(path);
          listValue.forEach(inClause::value);
          return inClause;
        } else {
          return cb.equal(path, value);
        }
      } catch (Exception e) {
        return null;
      }
    };
  }
}
