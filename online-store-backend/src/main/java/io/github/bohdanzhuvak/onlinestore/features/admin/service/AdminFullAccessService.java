package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.common.service.FullAccessService;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Admin service interface for full access operations with react-admin support.
 * Extends FullAccessService with admin-specific operations.
 */
public interface AdminFullAccessService<E extends BaseEntity, R extends AdminResponseDto, S extends AdminRequestDto, ID>
    extends FullAccessService<R, S, ID> {

  /**
   * Find all records with react-admin compatible filtering and sorting
   *
   * @param sort     Field to sort by
   * @param order    Sort order (ASC/DESC)
   * @param start    Start index for pagination
   * @param end      End index for pagination
   * @param filters  Map of filters to apply
   * @param pageable Pageable object
   * @return Page of filtered and sorted DTOs
   */
  Page<R> findAllWithFilters(String sort, String order, Integer start, Integer end,
                             Map<String, String> filters, Pageable pageable);

  /**
   * Delete multiple records by IDs
   *
   * @param ids List of IDs to delete
   */
  void deleteByIds(List<ID> ids);

  /**
   * Find records by filter criteria
   *
   * @param filters Map of filter criteria
   * @return List of filtered DTOs
   */
  List<R> findByFilters(Map<String, String> filters);

  /**
   * Check if admin has permission to perform operation on entity
   *
   * @param entityId  Entity ID to check
   * @param operation Operation type (READ, WRITE, DELETE)
   * @return true if permission granted
   */
  boolean hasPermission(ID entityId, String operation);

  /**
   * Get entity statistics for admin dashboard
   *
   * @return Map of statistics
   */
  Map<String, Object> getStatistics();

  /**
   * Get all records without pagination (react-admin compatible)
   * Used for reference data in react-admin
   */
  List<R> findAllForReference();
}
