package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.MenuDO;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 菜单Repo
 *
 * @author NotEdibleSalt
 */
public interface MenuRepo extends RepoBase<MenuDO> {

    Mono<MenuDO> findByRoutePathAndDelFalse(String routePath);

    Mono<Boolean> existsByParentIdAndDelFalse(String parentId);

}


