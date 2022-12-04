package com.ss.contains.light.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.controller.dto.command.SaveDictItemDTO;
import com.ss.contains.light.dos.DictItemDO;
import com.ss.contains.light.repository.DictItemRepo;
import com.ss.contains.light.service.DictItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @version 1.0
 * @date 2022-5-3
 */
@Service
@AllArgsConstructor
@Transactional
public class DictItemServiceImpl implements DictItemService {

    private final DictItemRepo dictItemRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<DictItemDO> getDictItems(String dictId) {

        return dictItemRepo.findDictItemDOByDictIdAndDelFalse(dictId);
    }

    @Override
    public Mono<DictItemDO> getDictItemById(String dictId, String dictItemId) {

        return dictItemRepo.findById(dictItemId)
                .doOnNext(dictItemDO -> {
                    if (!dictItemDO.getDictId().equals(dictId)) {
                        throw new Ex("该字典项不属于此字典");
                    }
                });
    }

    @Override
    public Mono<DictItemDO> addDictItem(String dictId, SaveDictItemDTO saveDictItemDTO) {

        DictItemDO dictItemDO = BeanUtil.copyProperties(saveDictItemDTO, DictItemDO.class);
        dictItemDO.setDictId(dictId);
        return r2dbcEntityTemplate.insert(dictItemDO);
    }

    @Override
    public Mono<DictItemDO> updateDictItem(String dictId, String id, SaveDictItemDTO saveDictItemDTO) {

        return dictItemRepo.findById(id)
                .switchIfEmpty(Mono.error(new Ex("字典不存在")))
                .doOnNext(dictItemDO -> {
                    if (!dictItemDO.getDictId().equals(dictId)) {
                        throw new Ex("该字典项不属于此字典");
                    }
                }).flatMap(dictItemDO -> {

                    BeanUtil.copyProperties(saveDictItemDTO, dictItemDO);
                    Mono<DictItemDO> save = dictItemRepo.save(dictItemDO);
                    return save;
                });
    }

    @Override
    public Mono<DictItemDO> delDictItem(String dictId, String id) {

        return dictItemRepo.findById(id)
                .doOnNext(dictItemDO -> {
                    if (!dictItemDO.getDictId().equals(dictId)) {
                        throw new Ex("该字典项不属于此字典");
                    }
                })
                .flatMap(dictItemDO -> {
                    dictItemDO.setDel(true);
                    return dictItemRepo.save(dictItemDO);
                });
    }

    @Override
    public Mono<Void> delAllDictItem(String dictId) {

        return dictItemRepo.deleteAllByDictId(dictId);
    }
}
