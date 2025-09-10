package io.github.bohdanzhuvak.onlinestore.common.service;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseRequestDto;
import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;

import java.util.List;

/**
 * A generic interface for services that provide full access to entities through DTOs,
 * including create, read, update, and delete operations.
 * <p>
 * This interface works with {@link BaseRequestDto} for input and {@link BaseResponseDto}
 * for output, ensuring that entity details are properly encapsulated and only exposed
 * via DTOs.
 *
 * @param <R>  the type of the response DTO, must extend {@link BaseResponseDto}
 * @param <S>  the type of the request DTO, must extend {@link BaseRequestDto}
 * @param <ID> the type of the entity identifier (e.g., Long, UUID)
 */
public interface FullAccessService<R extends BaseResponseDto, S extends BaseRequestDto, ID>
    extends ReadOnlyService<R, ID> {

  /**
   * Creates a new entity from the given request DTO.
   *
   * @param dto the request DTO containing entity data to save
   * @return the saved entity represented as a response DTO
   */
  R save(S dto);

  /**
   * Creates multiple entities from the given iterable of request DTOs.
   *
   * @param dtos the request DTOs to save
   * @return a list of saved entities represented as response DTOs
   */
  List<R> saveAll(Iterable<S> dtos);

  /**
   * Updates an existing entity identified by the given ID with the provided request DTO data.
   *
   * @param id  the identifier of the entity to update
   * @param dto the request DTO containing updated entity data
   * @return the updated entity represented as a response DTO
   */
  R update(ID id, S dto);

  /**
   * Deletes the entity identified by the given ID.
   *
   * @param id the identifier of the entity to delete
   */
  void delete(ID id);

  /**
   * Deletes multiple entities identified by the given iterable of IDs.
   *
   * @param ids the identifiers of the entities to delete
   */
  void deleteAll(Iterable<ID> ids);
}
