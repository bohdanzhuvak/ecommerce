package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.common.service.AbstractFullAccessService;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

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
  public Page<R> findAllWithFilters(String sort, String order, Integer start, Integer end,
                                    Map<String, String> filters, Pageable pageable) {

    // Apply sorting
    Sort.Direction direction = Sort.Direction.ASC;
    if (order != null && order.equalsIgnoreCase("DESC")) {
      direction = Sort.Direction.DESC;
    }

    String sortField = sort != null ? sort : "id";
    Sort sortObj = Sort.by(direction, sortField);

    // Apply pagination
    Pageable adminPageable;
    if (start != null && end != null) {
      int page = start / (end - start);
      int size = end - start;
      adminPageable = PageRequest.of(page, size, sortObj);
    } else {
      adminPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortObj);
    }

    // Apply filters (basic implementation - can be overridden)
    if (filters != null && !filters.isEmpty()) {
      return findByFiltersWithPagination(filters, adminPageable);
    }

    Page<E> entities = getRepository().findAll(adminPageable);
    return getMapper().toResponseDtoPage(entities);
  }

  @Override
  @Transactional
  public void deleteByIds(List<ID> ids) {
    if (ids == null || ids.isEmpty()) {
      return;
    }

    for (ID id : ids) {
      if (getRepository().existsById(id)) {
        getRepository().deleteById(id);
      }
    }
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
  protected Page<R> findByFiltersWithPagination(Map<String, String> filters, Pageable pageable) {
    // Basic implementation - can be overridden
    Page<E> entities = getRepository().findAll(pageable);
    return getMapper().toResponseDtoPage(entities);
  }
}
