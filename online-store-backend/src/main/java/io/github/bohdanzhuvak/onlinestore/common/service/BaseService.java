package io.github.bohdanzhuvak.onlinestore.common.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity, ID> {

  /**
   * Найти все записи с пагинацией
   */
  Page<T> findAll(Pageable pageable);

  /**
   * Найти все записи
   */
  List<T> findAll();

  /**
   * Найти запись по ID
   */
  Optional<T> findById(ID id);

  /**
   * Найти запись по ID или выбросить исключение
   */
  T findByIdOrThrow(ID id);

  /**
   * Проверить существование записи по ID
   */
  boolean existsById(ID id);

  /**
   * Сохранить запись
   */
  <S extends T> S save(S entity);

  /**
   * Сохранить все записи
   */
  <S extends T> List<S> saveAll(Iterable<S> entities);

  /**
   * Обновить запись
   */
  <S extends T> S update(S entity);

  /**
   * Удалить запись по ID
   */
  void deleteById(ID id);

  /**
   * Удалить запись
   */
  void delete(T entity);

  /**
   * Удалить все записи
   */
  void deleteAll(Iterable<? extends T> entities);

  /**
   * Удалить все записи
   */
  void deleteAll();

  /**
   * Подсчитать количество записей
   */
  long count();
}
