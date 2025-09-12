package io.github.bohdanzhuvak.onlinestore.common.service;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseRequestDto;
import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.mapper.BaseMapper;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFullAccessService<T extends BaseEntity, R extends BaseResponseDto, S extends BaseRequestDto, ID> extends AbstractReadOnlyService<T, R, S, ID> implements FullAccessService<R, S, ID> {

  protected abstract BaseRepository<T, ID> getRepository();

  protected abstract BaseMapper<T, R, S> getMapper();

  @Override
  @Transactional
  public R save(S dto) {
    T toSave = getMapper().toEntity(dto);
    T saved = getRepository().save(toSave);
    return getMapper().toResponseDto(saved);
  }

  @Override
  @Transactional
  public List<R> saveAll(Iterable<S> dtos) {
    List<T> toSave = getMapper().toEntityList((List<S>) dtos);
    List<T> saved = getRepository().saveAll(toSave);
    return getMapper().toResponseDtoList(saved);
  }

  @Override
  @Transactional
  public R update(ID id, S dto) {
    T existing = getRepository().findById(id).orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found"));
    getMapper().updateEntity(existing, dto);
    T updated = getRepository().save(existing);
    return getMapper().toResponseDto(updated);
  }

  @Override
  @Transactional
  public R delete(ID id) {
    T existing = getRepository().findById(id).orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found"));
    getRepository().deleteById(id);
    return getMapper().toResponseDto(existing);
  }

  @Override
  @Transactional
  public List<ID> deleteByIds(Iterable<ID> entities) {
    for (ID id : entities) {
      if (!getRepository().existsById(id)) {
        throw new NotFoundException("Entity with id " + id + " not found");
      }
    }
    getRepository().deleteAllById(entities);
    return new ArrayList<>((List<ID>) entities);
  }
}
