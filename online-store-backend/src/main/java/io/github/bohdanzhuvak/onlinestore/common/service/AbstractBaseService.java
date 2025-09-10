package io.github.bohdanzhuvak.onlinestore.common.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseService<T extends BaseEntity, ID> implements BaseService<T, ID> {

  protected abstract BaseRepository<T, ID> getRepository();

  @Override
  @Transactional(readOnly = true)
  public Page<T> findAll(Pageable pageable) {
    return getRepository().findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<T> findAll() {
    return getRepository().findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<T> findById(ID id) {
    return getRepository().findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public T findByIdOrThrow(ID id) {
    return findById(id)
        .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsById(ID id) {
    return getRepository().existsById(id);
  }

  @Override
  @Transactional
  public <S extends T> S save(S entity) {
    return getRepository().save(entity);
  }

  @Override
  @Transactional
  public <S extends T> List<S> saveAll(Iterable<S> entities) {
    return getRepository().saveAll(entities);
  }

  @Override
  @Transactional
  public <S extends T> S update(S entity) {
    if (entity.getId() == null) {
      throw new IllegalArgumentException("Entity ID cannot be null for update operation");
    }
    @SuppressWarnings("unchecked")
    ID entityId = (ID) entity.getId();
    if (!existsById(entityId)) {
      throw new NotFoundException("Entity with id " + entity.getId() + " not found");
    }
    return getRepository().save(entity);
  }

  @Override
  @Transactional
  public void deleteById(ID id) {
    if (!existsById(id)) {
      throw new NotFoundException("Entity with id " + id + " not found");
    }
    getRepository().deleteById(id);
  }

  @Override
  @Transactional
  public void delete(T entity) {
    getRepository().delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll(Iterable<? extends T> entities) {
    getRepository().deleteAll(entities);
  }

  @Override
  @Transactional
  public void deleteAll() {
    getRepository().deleteAll();
  }

  @Override
  @Transactional(readOnly = true)
  public long count() {
    return getRepository().count();
  }
}
