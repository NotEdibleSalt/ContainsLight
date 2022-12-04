package com.ss.contains.light.common.paging;

import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 * @version 1.0
 * @date 2022-5-1
 */
@FunctionalInterface
public interface FillParam<T> {

    Mono<PagingResult<T>> param(Pageable pageable, Query query);
}
