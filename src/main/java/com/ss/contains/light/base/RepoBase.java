package com.ss.contains.light.base;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 */
public interface RepoBase<T extends DOBase> extends R2dbcRepository<T, String> {


    default Mono<T> logicDelete(String id) {

        return findById(id)
                .flatMap(t -> {
                    t.del();
                    return save(t);
                });
    }

    default Mono<T> logicDelete(T t) {

        t.del();
        return save(t);
    }
}
