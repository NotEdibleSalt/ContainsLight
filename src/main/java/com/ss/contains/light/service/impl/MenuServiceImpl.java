package com.ss.contains.light.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.config.security.AdminUserDetails;
import com.ss.contains.light.controller.dto.command.SaveMenuDTO;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.MenuDO;
import com.ss.contains.light.repository.MenuRepo;
import com.ss.contains.light.service.MenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NotEdibleSalt
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepo menuRepo;


    @Override
    public Mono<MenuDO> getMenuById(String menuId) {

        return menuRepo.findById(menuId)
                .filter(DOBase::notDel);
    }

    @Override
    public Mono<MenuDO> addMenu(SaveMenuDTO saveMenuDTO) {

        MenuDO menuDO = saveMenuDTO.toMenuDO();

        return menuRepo.findByRoutePathAndDelFalse(menuDO.getRoutePath())
                .hasElement()
                .doOnNext(exist -> {
                    if (exist) {
                        throw new Ex("菜单已存在！");
                    }
                })
                .then(menuRepo.save(menuDO));
    }

    @Override
    public Mono<MenuDO> updateMenu(String menuId, SaveMenuDTO saveMenuDTO) {

        return menuRepo.findById(menuId)
                .doOnNext(menuDO -> {
                    if (ObjectUtil.isNull(menuDO)) {
                        throw new Ex("未找到该菜单");
                    }
                })
                .map(menuDO -> {

                    menuDO.setParentId(saveMenuDTO.getParentId());
                    menuDO.setName(saveMenuDTO.getName());
                    menuDO.setPath(saveMenuDTO.getPath());
                    menuDO.setDescription(saveMenuDTO.getDescription());
                    menuDO.setSortNumber(saveMenuDTO.getSortNumber());
                    menuDO.setRouteName(saveMenuDTO.getRouteName());
                    menuDO.setRoutePath(saveMenuDTO.getRoutePath());
                    menuDO.setStatus(saveMenuDTO.getStatus());

                    return menuDO;
                })
                .flatMap(menuRepo::save);

    }

    @Override
    public Mono<MenuDO> delMenu(String menuId) {

        return menuRepo.existsByParentIdAndDelFalse(menuId)
                .doOnNext(exist -> {
                    if (exist){
                        throw new Ex("该菜单存在下级菜单，不能删除");
                    }
                })
                .then(menuRepo.logicDelete(menuId));

    }


    @Override
    public Flux<MenuDO> getAllMenus() {

        return menuRepo.findAll()
                .filter(DOBase::notDel);
    }
}
