package com.ss.contains.light.service;

import com.ss.contains.light.config.security.AdminUserDetails;
import com.ss.contains.light.controller.dto.command.SaveMenuDTO;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.MenuDO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单管理Service
 *
 * @author NotEdibleSalt
 */

@Validated
public interface MenuService {

    /**
     * 根据id查询菜单信息
     *
     * @param menuId
     * @return com.ss.contains.light.dos.ArticleDO
     */
    Mono<MenuDO> getMenuById(String menuId);

    /**
     * 新增菜单
     *
     * @param saveMenuDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.MenuDO>
     */
    Mono<MenuDO> addMenu(@Valid SaveMenuDTO saveMenuDTO);

    /**
     * 更新菜单
     *
     * @param menuId 菜单id
     * @param saveMenuDTO 保存菜单DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.MenuDO>
     */
    Mono<MenuDO> updateMenu(String menuId, @Valid SaveMenuDTO saveMenuDTO);

    /**
     * 根据id删除菜单
     *
     * @param menuId 菜单id
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    Mono<MenuDO> delMenu(String menuId);


    Flux<MenuDO> getAllMenus();

}
