package com.ss.contains.light.common.paging;

import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import org.springframework.data.relational.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.relational.core.query.Criteria.where;

/**
 * @author NotEdibleSalt
 * @version 1.0
 * @date 2022-5-1
 */
public interface TemplateFunction<T> {

    ReactiveSelectOperation.SelectWithProjection<T> template();

    static List<Criteria> baseQuery() {

        List<Criteria> criteriaList = new ArrayList<>();
        Criteria criteria = where("del").is(false);
        criteriaList.add(criteria);
        return criteriaList;
    }
}
