package io.github.bohdanzhuvak.onlinestore.legacy.common.service;

import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.legacy.common.exception.impl.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * A generic interface for read-only services that provide methods to retrieve entities as DTOs.
 * <p>
 * This interface works with {@link BaseResponseDto} subclasses, ensuring that only DTO representations
 * of entities are exposed to the client. It supports retrieval of all entities, paginated results,
 * and fetching by identifier.
 *
 * @param <R>  the type of the response DTO, must extend {@link BaseResponseDto}
 * @param <ID> the type of the entity identifier (e.g., Long, UUID)
 */
public interface ReadOnlyService<R extends BaseResponseDto, ID> {

  /**
   * Retrieves all entities as a list of DTOs.
   *
   * @return a list of all entities represented as DTOs
   */
  List<R> findAll();

  /**
   * Retrieves a paginated list of entities as DTOs.
   *
   * @param pageable the pagination information (page number, size, sorting)
   * @return a page of entities represented as DTOs
   */
  Page<R> findAll(Pageable pageable);

  /**
   * Retrieves an entity by its identifier.
   *
   * @param id the identifier of the entity to retrieve
   * @return the entity represented as a DTO
   * @throws NotFoundException if the entity is not found
   */
  R findById(ID id);
}
