package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.dos.RoleMenuDO;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 保存角色关联的菜单参数
 *
 * @author NotEdibleSalt
 */
@Data
public class RoleAssociatedMenusSave {

    private Set<String> menuIdSet;


    public Set<RoleMenuDO> toRoleMenuDOSet(String roleId) {

        return menuIdSet.stream()
                .map(menuId -> {
                    RoleMenuDO roleMenuDO = new RoleMenuDO();
                    roleMenuDO.setRoleId(roleId);
                    roleMenuDO.setMenuId(menuId);
                    return roleMenuDO;
                })
                .collect(Collectors.toSet());

    }
}
