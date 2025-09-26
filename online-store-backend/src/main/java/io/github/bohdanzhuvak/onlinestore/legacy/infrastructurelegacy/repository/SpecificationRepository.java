package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Repository interface for specification-based queries.
 * This interface provides methods for querying entities using JPA Specifications.
 */
public interface SpecificationRepository<T extends BaseEntity, ID> {

  /**
   * Find all entities matching the given specification
   */
  List<T> findAll(Specification<T> spec);

  /**
   * Find all entities matching the given specification with pagination
   */
  Page<T> findAll(Specification<T> spec, Pageable pageable);

  /**
   * Count entities matching the given specification
   */
  long count(Specification<T> spec);
}
