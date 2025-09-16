package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Base repository interface for all domain entities.
 * This interface defines the contract for basic CRUD operations without depending on Spring Data JPA.
 */
public interface BaseRepository<T extends BaseEntity, ID> {

  /**
   * Find all records with pagination
   */
  Page<T> findAll(Pageable pageable);

  /**
   * Find all records
   */
  List<T> findAll();

  /**
   * Find record by ID
   */
  Optional<T> findById(ID id);

  /**
   * Check if record exists by ID
   */
  boolean existsById(ID id);

  /**
   * Save a record
   */
  <S extends T> S save(S entity);

  /**
   * Save multiple records
   */
  <S extends T> List<S> saveAll(Iterable<S> entities);

  /**
   * Delete record by ID
   */
  void deleteById(ID id);

  /**
   * Delete a record
   */
  void delete(T entity);

  /**
   * Delete multiple records
   */
  void deleteAll(Iterable<? extends T> entities);

  /**
   * Delete multiple records by IDs
   */
  void deleteAllById(Iterable<? extends ID> ids);

  /**
   * Delete all records
   */
  void deleteAll();

  /**
   * Count total records
   */
  long count();
}
