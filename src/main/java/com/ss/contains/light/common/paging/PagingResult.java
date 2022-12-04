package com.ss.contains.light.common.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @version 1.0
 * @date 2022-5-26
 */
@SuppressWarnings("unchecked")
@Data
@AllArgsConstructor
public class PagingResult<T> {

    private T list;

    private Long total;

    public static <T> FillParam<T> templatePaging(TemplateFunction<T> templateFun) {

        Sort sort = Sort.by(Sort.Order.desc("updateAt"));
        return (pageable, query) -> templateFun.template()
                .matching(query.offset(pageable.getOffset()).limit(pageable.getPageSize()).sort(pageable.getSortOr(sort)))
                .all()
                .collectList()
                .zipWith(templateFun.template().matching(query).count())
                .map(t -> new PagingResult(t.getT1(), t.getT2()));
    }

    /**
     * 物理分页
     *
     * @param pageable
     * @param data
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < T>>
     * @date 2022-5-2
     */
    public static <T> Mono<PagingResult<T>> physicalPaging(Pageable pageable, Flux<T> data) {

        Mono<Long> count = data.count();
        Mono<List<T>> listMono = data
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limitRate(pageable.getPageSize())
                .collectList();

        return listMono.zipWith(count)
                .map(tuple -> new PagingResult<>((T) tuple.getT1(), tuple.getT2()));

    }


}
