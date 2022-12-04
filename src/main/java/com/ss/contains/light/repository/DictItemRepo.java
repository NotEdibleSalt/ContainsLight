package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.DictItemDO;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典项Repo
 *
 * @author NotEdibleSalt
 * @date 2022-5-2
 */
public interface DictItemRepo extends RepoBase<DictItemDO> {

    Flux<DictItemDO> findDictItemDOByDictIdAndDelFalse(String dictId);

    /**
     * 删除所有字典项
     *
     * @param dictId 字典id
     * @return void
     * @date 2022-5-3
     **/
    @Modifying
    @Query("update dict_item set del = true where dict_id = :dictId")
    Mono<Void> deleteAllByDictId(String dictId);
}


