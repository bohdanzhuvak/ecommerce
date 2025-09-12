package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.common.service.ReadOnlyService;

/**
 * A generic interface for read-only customer services.
 * This interface is specifically designed for customer-facing read-only functionality.
 *
 * @param <R>  Response DTO type
 * @param <ID> ID type
 */
public interface BaseCustomerService<R extends BaseResponseDto, ID> extends ReadOnlyService<R, ID> {

}
