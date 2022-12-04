package com.ss.contains.light.service;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.SaveDictDTO;
import com.ss.contains.light.controller.dto.query.DictsPagingQuery;
import com.ss.contains.light.dos.DictDO;
import com.ss.contains.light.dos.DictItemDO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @version 1.0
 * @date 2022-5-2
 */
@Validated
public interface DictService {

    /**
     * 分页查询字典
     *
     * @param dictsPagingQuery 分页查询字典参数
     * @return com.ss.contains.light.common.paging.PagingResult
     * @date 2022-5-2
     **/
    Mono<PagingResult<DictDO>> dictPaging(DictsPagingQuery dictsPagingQuery);

    /**
     * 根据id查询字典
     *
     * @param id 字典id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictDO>
     * @date 2022-5-2
     **/
    Mono<DictDO> dictById(String id);

    /**
     * 新增字典
     *
     * @param addDictDTO 新增字典DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictDO>
     * @date 2022-5-2
     **/
    Mono<DictDO> addDict(@Valid SaveDictDTO addDictDTO);


    /**
     * 删除字典
     *
     * @param id 字典id
     * @return reactor.core.publisher.Flux<java.lang.Void>
     * @date 2022-5-3
     **/
    Mono<Void> delDict(String id);

    /**
     * 更新字典
     *
     * @param id          字典id
     * @param saveDictDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictDO>
     * @date 2022-5-1
     **/
    Mono<DictDO> updateDict(String id, @Valid SaveDictDTO saveDictDTO);

    /**
     * 根据字典名获取所有字典项
     *
     * @param dictType 字典类型
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.DictItemDO>
     */
    Flux<DictItemDO> getDictItemsByDictName(String dictType);
}
