package com.ss.contains.light.common.paging;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author NotEdibleSalt
 * @version 1.0
 * @date 2022-5-1
 */
@Data
public class PagingParam {

    private Integer pageNumber = 1;

    private Integer pageSize = 10;

    public Pageable toPageable() {
        return Pageable.ofSize(pageSize).withPage(pageNumber - 1);
    }

    public Pageable toPageable(Sort sort) {

        PageRequest pageable = (PageRequest) Pageable.ofSize(pageSize).withPage(pageNumber - 1);
        pageable = pageable.withSort(sort);

        return pageable;
    }


}
