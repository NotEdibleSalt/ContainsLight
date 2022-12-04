package com.ss.contains.light.service;

import com.ss.contains.light.controller.dto.command.SaveDictItemDTO;
import com.ss.contains.light.dos.DictItemDO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * 字典项Service
 *
 * @version 1.0
 * @date 2022-5-3
 */
@Validated
public interface DictItemService {

    /**
     * 获取字典的全部字典项
     *
     * @param dictId 字典id
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.DictItemDO>
     * @date 2022-5-3
     **/
    Flux<DictItemDO> getDictItems(String dictId);

    /**
     * 查询字典项
     *
     * @param dictId     字典id
     * @param dictItemId 字典项id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictItemDO>
     */
    Mono<DictItemDO> getDictItemById(String dictId, String dictItemId);

    /**
     * 新增字典项
     *
     * @param dictId          字典id
     * @param saveDictItemDTO 新增字典项
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictItemDO>
     * @date 2022-5-3
     **/
    Mono<DictItemDO> addDictItem(String dictId, @Valid SaveDictItemDTO saveDictItemDTO);

    /**
     * 修改字典项
     *
     * @param dictId          字典id
     * @param id              字典项id
     * @param saveDictItemDTO 保存字典项DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictItemDO>
     * @date 2022-5-3
     **/
    Mono<DictItemDO> updateDictItem(String dictId, String id, @Valid SaveDictItemDTO saveDictItemDTO);

    /**
     * 删除字典项
     *
     * @param dictId 字典id
     * @param id     字典项id
     * @return void
     * @date 2022-5-3
     **/
    Mono<DictItemDO> delDictItem(String dictId, String id);

    /**
     * 删除所有字典项
     *
     * @param dictId 字典id
     * @return void
     * @date 2022-5-3
     **/
    Mono<Void> delAllDictItem(String dictId);


}
