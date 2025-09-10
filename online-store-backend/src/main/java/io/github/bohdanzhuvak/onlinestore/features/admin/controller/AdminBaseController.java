package io.github.bohdanzhuvak.onlinestore.features.admin.controller;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminFullAccessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Base controller for admin functionality with react-admin support.
 * Controller only handles HTTP requests/responses, all business logic is in service layer.
 *
 * @param <E>  Entity type
 * @param <S>  Request DTO type
 * @param <R>  Response DTO type
 * @param <ID> ID type
 */
public abstract class AdminBaseController<E extends BaseEntity, S extends AdminRequestDto, R extends AdminResponseDto, ID> {

  protected abstract AdminFullAccessService<E, R, S, ID> getAdminService();

  /**
   * Get all records with react-admin compatible filtering and sorting
   * Supports: _sort, _order, _start, _end, _filter parameters
   */
  @GetMapping
  public ResponseEntity<Page<R>> getAll(
      @RequestParam(required = false) String _sort,
      @RequestParam(required = false) String _order,
      @RequestParam(required = false) Integer _start,
      @RequestParam(required = false) Integer _end,
      @RequestParam(required = false) Map<String, String> _filter,
      Pageable pageable) {

    Page<R> dtos = getAdminService().findAllWithFilters(_sort, _order, _start, _end, _filter, pageable);
    return ResponseEntity.ok(dtos);
  }

  /**
   * Get single record by ID (react-admin compatible)
   */
  @GetMapping("/{id}")
  public ResponseEntity<R> getOne(@PathVariable ID id) {
    R dto = getAdminService().findById(id);
    return ResponseEntity.ok(dto);
  }

  /**
   * Create new record (react-admin compatible)
   */
  @PostMapping
  public ResponseEntity<R> create(@RequestBody S requestDto) {
    R responseDto = getAdminService().save(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  /**
   * Update existing record (react-admin compatible)
   */
  @PutMapping("/{id}")
  public ResponseEntity<R> update(@PathVariable ID id, @RequestBody S requestDto) {
    R responseDto = getAdminService().update(id, requestDto);
    return ResponseEntity.ok(responseDto);
  }

  /**
   * Delete single record (react-admin compatible)
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable ID id) {
    getAdminService().delete(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Bulk delete multiple records (react-admin compatible)
   * Expects array of IDs in request body
   */
  @DeleteMapping
  public ResponseEntity<Void> deleteMany(@RequestBody List<ID> ids) {
    getAdminService().deleteByIds(ids);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get total count of records (react-admin compatible)
   */
  @GetMapping("/count")
  public ResponseEntity<Map<String, Long>> getCount() {
    long count = getAdminService().findAll().size();
    return ResponseEntity.ok(Map.of("total", count));
  }

  /**
   * Get all records without pagination (react-admin compatible)
   * Used for reference data in react-admin
   */
  @GetMapping("/all")
  public ResponseEntity<List<R>> getAllWithoutPagination() {
    List<R> dtos = getAdminService().findAllForReference();
    return ResponseEntity.ok(dtos);
  }
}
