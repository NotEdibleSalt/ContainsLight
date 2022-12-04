package com.ss.contains.light.controller;

import com.ss.contains.light.common.MessageCommon;
import com.ss.contains.light.common.R;
import com.ss.contains.light.controller.dto.command.SaveMenuDTO;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.MenuDO;
import com.ss.contains.light.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 菜单管理
 *
 * @author NotEdibleSalt
 */
@RequestMapping("menus")
@RestController
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 根据id查询菜单信息
     *
     * @param menuId
     * @return com.ss.contains.light.dos.ArticleDO
     */
    @GetMapping("{menuId}")
    public Mono<MenuDO> getMenuById(@PathVariable("menuId") String menuId) {

        return menuService.getMenuById(menuId);
    }

    /**
     * 新增菜单
     *
     * @param saveMenuDTO
     * @return com.ss.contains.light.dos.ArticleDO
     */
    @PostMapping()
    public Mono<MenuDO> addMenu(@RequestBody SaveMenuDTO saveMenuDTO) {

        return menuService.addMenu(saveMenuDTO);
    }

    /**
     * 更新菜单
     *
     * @param menuId 菜单id
     * @param saveMenuDTO 保存菜单DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.MenuDO>
     */
    @PutMapping("{menuId}")
    public Mono<MenuDO> updateMenu(@PathVariable("menuId") String menuId, @RequestBody SaveMenuDTO saveMenuDTO) {

        return menuService.updateMenu(menuId, saveMenuDTO);
    }

    /**
     * 根据id删除菜单
     *
     * @param menuId 菜单id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.MenuDO>
     */
    @DeleteMapping("{menuId}")
    public Mono<R<Object>> delMenu(@PathVariable("menuId") String menuId) {

        return menuService.delMenu(menuId).thenReturn(R.message(MessageCommon.DEL_SUCCESS));
    }

    /**
     * 查询菜单树
     *
     * @return reactor.core.publisher.Mono<java.util.List<com.ss.contains.light.controller.ro.TreeDataRO>>
     */
    @GetMapping("tree")
    public Mono<List<TreeDataRO>> getMenuTree(){

        return menuService.getMenuTree();
    }


}
