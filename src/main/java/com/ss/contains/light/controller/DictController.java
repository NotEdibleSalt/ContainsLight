package com.ss.contains.light.controller;

import com.ss.contains.light.common.MessageCommon;
import com.ss.contains.light.common.R;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.SaveDictDTO;
import com.ss.contains.light.controller.dto.command.SaveDictItemDTO;
import com.ss.contains.light.controller.dto.query.DictsPagingQuery;
import com.ss.contains.light.dos.DictDO;
import com.ss.contains.light.dos.DictItemDO;
import com.ss.contains.light.service.DictItemService;
import com.ss.contains.light.service.DictService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典相关接口
 *
 * @version 1.0
 * @date 2022-5-2
 */
@RequestMapping("dicts")
@RestController
@AllArgsConstructor
public class DictController {

    private final DictService dictService;
    private final DictItemService dictItemService;

    /**
     * 分页查询字典
     *
     * @param dictsPagingQuery 分页查询字典参数
     * @return com.ss.contains.light.common.paging.PagingResult
     * @date 2022-5-2
     **/
    @GetMapping()
    public Mono<PagingResult<DictDO>> dictPaging(DictsPagingQuery dictsPagingQuery) {

        return dictService.dictPaging(dictsPagingQuery);
    }

    /**
     * 根据id查询字典
     *
     * @param id 字典id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictDO>
     * @date 2022-5-2
     **/
    @GetMapping("/{id}")
    public Mono<DictDO> dictById(@PathVariable("id") String id) {

        return dictService.dictById(id);
    }

    /**
     * 新增字典
     *
     * @param addDictDTO 新增字典DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictDO>
     * @date 2022-5-2
     **/
    @PostMapping("")
    public Mono<DictDO> addDict(@RequestBody SaveDictDTO addDictDTO) {

        return dictService.addDict(addDictDTO);
    }

    /**
     * 更新字典
     *
     * @param id          字典id
     * @param saveDictDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictDO>
     * @date 2022-5-1
     **/
    @PutMapping("/{id}")
    public Mono<DictDO> updateDict(@PathVariable("id") String id, @RequestBody SaveDictDTO saveDictDTO) {

        return dictService.updateDict(id, saveDictDTO);
    }

    /**
     * 删除字典
     *
     * @param id 字典id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.R>
     * @date 2022-5-2
     **/
    @DeleteMapping("{id}")
    public Mono<R<Object>> delDict(@PathVariable("id") String id) {

        return dictService.delDict(id).thenReturn(R.message(MessageCommon.DEL_SUCCESS));
    }

    /**
     * 获取字典的全部字典项
     *
     * @param dictId 字典id
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.DictItemDO>
     * @date 2022-5-3
     **/
    @GetMapping("{dictId}/items")
    public Flux<DictItemDO> getDictItems(@PathVariable("dictId") String dictId) {

        return dictItemService.getDictItems(dictId);
    }

    /**
     * 根据字典名获取所有字典项
     *
     * @param dictType 字典类型
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.DictItemDO>
     */
    @GetMapping("type/{dictType}/items")
    public Flux<DictItemDO> getDictItemsByDictName(@PathVariable("dictType") String dictType) {

        return dictService.getDictItemsByDictName(dictType);
    }

    /**
     * 查询字典项
     *
     * @param dictId     字典id
     * @param dictItemId 字典项id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictItemDO>
     */
    @GetMapping("{dictId}/items/{dictItemId}")
    public Mono<DictItemDO> getDictItemById(@PathVariable("dictId") String dictId, @PathVariable("dictItemId") String dictItemId) {

        return dictItemService.getDictItemById(dictId, dictItemId);
    }

    /**
     * 新增字典项
     *
     * @param dictId          字典id
     * @param saveDictItemDTO 保存字典项DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictItemDO>
     * @date 2022-5-3
     **/
    @PostMapping("{dictId}/items")
    public Mono<DictItemDO> addDictItem(@PathVariable("dictId") String dictId, @RequestBody SaveDictItemDTO saveDictItemDTO) {

        return dictItemService.addDictItem(dictId, saveDictItemDTO);
    }

    /**
     * 修改字典项
     *
     * @param dictId          字典id
     * @param id              字典项id
     * @param saveDictItemDTO 保存字典项DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.DictItemDO>
     * @date 2022-5-3
     **/
    @PutMapping("{dictId}/items/{id}")
    public Mono<DictItemDO> updateDictItem(@PathVariable("dictId") String dictId, @PathVariable("id") String id,
                                           @RequestBody SaveDictItemDTO saveDictItemDTO) {

        return dictItemService.updateDictItem(dictId, id, saveDictItemDTO);
    }

    /**
     * 删除字典项
     *
     * @param dictId 字典id
     * @param id     字典项id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.R < java.lang.Object>>
     */
    @DeleteMapping("{dictId}/items/{id}")
    public Mono<R<Object>> delDictItem(@PathVariable("dictId") String dictId, @PathVariable("id") String id) {

        return dictItemService.delDictItem(dictId, id).thenReturn(R.message(MessageCommon.DEL_SUCCESS));
    }

}
