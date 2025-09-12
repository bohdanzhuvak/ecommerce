package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseRequestDto;
import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.common.service.AbstractReadOnlyService;
import io.github.bohdanzhuvak.onlinestore.domain.model.BaseEntity;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BaseRepository;
import io.github.bohdanzhuvak.onlinestore.features.customer.mapper.BaseCustomerMapper;

/**
 * Abstract implementation of ReadOnlyCustomerService.
 * Provides common read-only functionality for customer-facing services.
 */
public abstract class AbstractCustomerService<T extends BaseEntity, R extends BaseResponseDto, S extends BaseRequestDto, ID> extends AbstractReadOnlyService<T, R, S, ID>
    implements BaseCustomerService<R, ID> {

  protected abstract BaseRepository<T, ID> getRepository();

  protected abstract BaseCustomerMapper<T, R, S> getMapper();
}
