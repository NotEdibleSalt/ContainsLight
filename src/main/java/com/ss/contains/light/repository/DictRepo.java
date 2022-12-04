package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.DictDO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.util.annotation.Nullable;

/**
 * 字典表Repo
 *
 * @author NotEdibleSalt
 * @date 2022-5-2
 */
public interface DictRepo extends RepoBase<DictDO> {

    Flux<DictDO> findDictDOByNameLikeAndTypeLike(Pageable pageable, @Nullable String name, @Nullable String type);

    Flux<DictDO> findDictDOByTypeEqualsAndDelFalse(String name);

}


