package io.github.bohdanzhuvak.onlinestore.common.controller;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseDto;
import io.github.bohdanzhuvak.onlinestore.common.mapper.BaseMapper;
import io.github.bohdanzhuvak.onlinestore.common.service.BaseService;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
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

import java.util.List;

public abstract class BaseController<E extends BaseEntity, D extends BaseDto, ID> {

  protected abstract BaseService<E, ID> getService();

  protected abstract BaseMapper<E, D> getMapper();

  /**
   * Get all records with pagination
   */
  @GetMapping
  public ResponseEntity<Page<D>> findAll(Pageable pageable) {
    Page<E> entities = getService().findAll(pageable);
    Page<D> dtos = entities.map(getMapper()::toDto);
    return ResponseEntity.ok(dtos);
  }

  /**
   * Get all records without pagination
   */
  @GetMapping("/all")
  public ResponseEntity<List<D>> findAll() {
    List<E> entities = getService().findAll();
    List<D> dtos = getMapper().toDtoList(entities);
    return ResponseEntity.ok(dtos);
  }

  /**
   * Get record by ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<D> findById(@PathVariable ID id) {
    E entity = getService().findByIdOrThrow(id);
    D dto = getMapper().toDto(entity);
    return ResponseEntity.ok(dto);
  }

  /**
   * Create a new record
   */
  @PostMapping
  public ResponseEntity<D> create(@RequestBody D dto) {
    E entity = getMapper().toEntityForCreate(dto);
    E savedEntity = getService().save(entity);
    D savedDto = getMapper().toDto(savedEntity);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
  }

  /**
   * Update an existing record by ID
   */
  @PutMapping("/{id}")
  public ResponseEntity<D> update(@PathVariable ID id, @RequestBody D dto) {
    E entity = getService().findByIdOrThrow(id);
    getMapper().updateEntity(entity, dto);
    E updatedEntity = getService().update(entity);
    D updatedDto = getMapper().toDto(updatedEntity);
    return ResponseEntity.ok(updatedDto);
  }

  /**
   * Delete a record by ID
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable ID id) {
    getService().deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get total count of records
   */
  @GetMapping("/count")
  public ResponseEntity<Long> count() {
    long count = getService().count();
    return ResponseEntity.ok(count);
  }
}
