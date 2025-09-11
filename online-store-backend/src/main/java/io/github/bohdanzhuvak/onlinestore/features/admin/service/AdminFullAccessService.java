package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.common.service.FullAccessService;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Admin service interface for full access operations with react-admin support.
 * Extends FullAccessService with admin-specific operations.
 */
public interface AdminFullAccessService<E extends BaseEntity, R extends AdminResponseDto, S extends AdminRequestDto, ID>
    extends FullAccessService<R, S, ID> {

  /**
   * Find all records with pagination, sorting, and filtering (react-admin compatible)
   *
   * @param sort    Field to sort by
   * @param order   Sort order (ASC or DESC)
   * @param page    Page number (1-based)
   * @param perPage Records per page
   * @param filters Map of filter criteria
   * @return Page of filtered DTOs
   */
  Page<R> findAllWithFilters(
      String sort,
      String order,
      int page,
      int perPage,
      Map<String, Object> filters);

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
