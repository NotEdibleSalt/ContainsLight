package com.ss.contains.light.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.common.paging.TemplateFunction;
import com.ss.contains.light.controller.dto.command.SaveDictDTO;
import com.ss.contains.light.controller.dto.query.DictsPagingQuery;
import com.ss.contains.light.dos.DictDO;
import com.ss.contains.light.dos.DictItemDO;
import com.ss.contains.light.repository.DictRepo;
import com.ss.contains.light.service.DictItemService;
import com.ss.contains.light.service.DictService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @version 1.0
 * @date 2022-5-2
 */
@Service
@AllArgsConstructor
@Transactional
public class DictServiceImpl implements DictService {

    private final DictRepo dictRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final DictItemService dictItemService;

    @Override
    public Mono<PagingResult<DictDO>> dictPaging(DictsPagingQuery dictsPagingQuery) {

        List<Criteria> criteriaList = TemplateFunction.baseQuery();
        if (StrUtil.isNotBlank(dictsPagingQuery.getName())) {
            Criteria name = Criteria.where("name").like(dictsPagingQuery.getName() + "%");
            criteriaList.add(name);
        }
        if (StrUtil.isNotBlank(dictsPagingQuery.getType())) {
            Criteria type = Criteria.where("type").like(dictsPagingQuery.getType() + "%");
            criteriaList.add(type);
        }

        Query query = Query.query(Criteria.from(criteriaList));

        TemplateFunction<DictDO> templateFun = () -> r2dbcEntityTemplate.select(DictDO.class).from("dict");
        return PagingResult.templatePaging(templateFun).param(dictsPagingQuery.toPageable(), query);

    }

    @Override
    public Mono<DictDO> dictById(String id) {
        return dictRepo.findById(id);
    }

    @Override
    public Mono<DictDO> addDict(SaveDictDTO addDictDTO) {

        DictDO dictDO = addDictDTO.to();
        return r2dbcEntityTemplate.insert(dictDO);
    }


    @Override
    public Mono<DictDO> updateDict(String id, SaveDictDTO saveDictDTO) {

        DictDO dictDO = saveDictDTO.to();
        dictDO.setId(id);
        return dictRepo.save(dictDO);
    }

    @Override
    @Transactional
    public Mono<Void> delDict(String id) {

        return dictRepo.findById(id)
                .flatMap(dictDO -> {
                    if (dictDO.getSystem()) {
                        return Mono.error(new Ex("不能删除系统内置字典"));
                    }
                    dictDO.setDel(true);

                    return dictRepo.save(dictDO).then(dictItemService.delAllDictItem(id));
                });
    }

    @Override
    public Flux<DictItemDO> getDictItemsByDictName(String dictType) {

        return dictRepo.findDictDOByTypeEqualsAndDelFalse(dictType)
                .flatMap(dictDO -> dictItemService.getDictItems(dictDO.getId()));
    }
}
