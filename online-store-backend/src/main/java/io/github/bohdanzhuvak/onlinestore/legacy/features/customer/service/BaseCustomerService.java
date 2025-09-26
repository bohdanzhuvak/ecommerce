package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.legacy.common.dto.BaseResponseDto;
import io.github.bohdanzhuvak.onlinestore.legacy.common.service.ReadOnlyService;

/**
 * A generic interface for read-only customer services.
 * This interface is specifically designed for customer-facing read-only functionality.
 *
 * @param <R>  Response DTO type
 * @param <ID> ID type
 */
public interface BaseCustomerService<R extends BaseResponseDto, ID> extends ReadOnlyService<R, ID> {

}
