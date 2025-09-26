package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * JPA implementation of BaseRepository.
 * This class provides JPA-specific implementation for the domain repository interface.
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseEntity, ID> extends
    JpaRepository<T, ID>,
    JpaSpecificationExecutor<T>,
    BaseRepository<T, ID>,
    SpecificationRepository<T, ID> {
}
