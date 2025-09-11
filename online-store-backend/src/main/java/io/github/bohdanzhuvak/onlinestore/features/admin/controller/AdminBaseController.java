package io.github.bohdanzhuvak.onlinestore.features.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminFullAccessService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base controller for admin functionality with react-admin support.
 *
 * @param <E>  Entity type
 * @param <S>  Request DTO type
 * @param <R>  Response DTO type
 * @param <ID> ID type
 */
public abstract class AdminBaseController<
    E extends BaseEntity,
    S extends AdminRequestDto,
    R extends AdminResponseDto,
    ID> {

  protected abstract AdminFullAccessService<E, R, S, ID> getAdminService();

  /**
   * Get all records (react-admin compatible).
   * Query params: sort, order, page, perPage, filter
   */
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAll(
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String order,
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int perPage,
      @RequestParam(required = false) String filter) throws JsonProcessingException {

    Map<String, Object> filterMap = new HashMap<>();
    if (filter != null && !filter.isBlank()) {
      ObjectMapper mapper = new ObjectMapper();
      filterMap = mapper.readValue(filter, new TypeReference<Map<String, Object>>() {
      });
    }

    Page<R> dtos = getAdminService().findAllWithFilters(sort, order, page, perPage, filterMap);

    Map<String, Object> response = new HashMap<>();
    response.put("data", dtos.getContent());
    response.put("total", dtos.getTotalElements());

    return ResponseEntity.ok(response);
  }

  /**
   * Get single record (react-admin compatible).
   */
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getOne(@PathVariable ID id) {
    R dto = getAdminService().findById(id);
    return ResponseEntity.ok(Map.of("data", dto));
  }

  /**
   * Create new record (react-admin compatible).
   */
  @PostMapping
  public ResponseEntity<Map<String, Object>> create(@RequestBody S requestDto) {
    R responseDto = getAdminService().save(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", responseDto));
  }

  /**
   * Update existing record (react-admin compatible).
   */
  @PutMapping("/{id}")
  public ResponseEntity<Map<String, Object>> update(@PathVariable ID id, @RequestBody S requestDto) {
    R responseDto = getAdminService().update(id, requestDto);
    return ResponseEntity.ok(Map.of("data", responseDto));
  }

  /**
   * Delete single record (react-admin compatible).
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> delete(@PathVariable ID id) {
    R deleted = getAdminService().delete(id);
    return ResponseEntity.ok(Map.of("data", deleted));
  }

  /**
   * Bulk delete multiple records (react-admin compatible).
   */
  @DeleteMapping
  public ResponseEntity<Map<String, Object>> deleteMany(@RequestBody List<ID> ids) {
    List<ID> deletedIds = getAdminService().deleteByIds(ids);
    return ResponseEntity.ok(Map.of("data", deletedIds));
  }

  /**
   * Get all records without pagination (used for reference inputs).
   */
  @GetMapping("/all")
  public ResponseEntity<Map<String, Object>> getAllWithoutPagination() {
    List<R> dtos = getAdminService().findAllForReference();
    return ResponseEntity.ok(Map.of("data", dtos));
  }
}
