package io.github.bohdanzhuvak.onlinestore.legacy.common.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BaseRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseRequestDto;
import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.legacy.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.legacy.common.mapper.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractReadOnlyService<T extends BaseEntity, R extends BaseResponseDto, S extends BaseRequestDto, ID> implements ReadOnlyService<R, ID> {

  protected abstract BaseRepository<T, ID> getRepository();

  protected abstract BaseMapper<T, R, S> getMapper();

  @Override
  @Transactional(readOnly = true)
  public Page<R> findAll(Pageable pageable) {
    Page<T> entities = getRepository().findAll(pageable);
    return getMapper().toResponseDtoPage(entities);
  }

  @Override
  @Transactional(readOnly = true)
  public List<R> findAll() {
    List<T> entities = getRepository().findAll();
    return getMapper().toResponseDtoList(entities);
  }

  @Override
  @Transactional(readOnly = true)
  public R findById(ID id) {
    T entity = getRepository().findById(id).orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found"));
    return getMapper().toResponseDto(entity);
  }
}
